package API.service;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.Product;
import API.entites.User;
import API.entites.enums.OrderStatus;
import API.repository.OrderItemRepository;
import API.repository.OrderRepository;
import API.repository.ProductRepository;
import API.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderItemRepository itemRepository;

	public List<Order> findAll(){
		return repository.findAll();
	}

	public Order findById(Long id){
		return repository.findById(id).orElseThrow(()
				-> new IllegalArgumentException("Id não encontrado"));
	}

	public List<Order> findByUserCpf(String cpf){
		try {
			return  repository.findByUserCpf(cpf);
		}catch (EntityNotFoundException e){
			throw new IllegalArgumentException("Usuario não encotrado");
		}
	}

	/*
		//tentei de tudo, a unica solução que encotrei foi separar o Cpf ates de ir pro service
		ou mandar o OrderRequest para o service
	*/

	public Order insert(Order order, String cpf) {
		User user = userRepository.findById(cpf).orElseThrow(
				() -> new IllegalArgumentException(" Usuário não encontrado"));

		order.setUser(user);

		for (OrderItem item : order.getItems()) {
			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));

			product.removeStock(item.getQuantity()); // ja verifica o stock
			productRepository.save(product);

			item.setProduct(product);
			item.setPrice(product.getPrice());
		}

		Order savedOrder = repository.save(order);

		//.forEach simplifica o for
		order.getItems().forEach(item -> { //associando o item ao order salvo
			item.setOrder(savedOrder); itemRepository.save(item);
		});

		return savedOrder;
	}

	public Order update(Long orderId, List<OrderItem> updatedItems) {
		Order order = repository.findById(orderId).orElseThrow(
				() -> new IllegalArgumentException("Pedido não encontrado"));

		if(order.getStatus() != OrderStatus.AGUARDANDO_PAGAMENTO){
			throw new IllegalArgumentException("Pagamento já foi efetuado, gerar um novo pedido");
		}

		for (OrderItem item : updatedItems) {
			OrderItem checkItem = order.getItems().stream()
					.filter(orderItem -> orderItem.getProduct().getId().equals(item.getProduct().getId()))
					.findFirst() //não precisa percorrer tudo já que existe apenas um Id para cada produto
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no pedido"));

			int difference = item.getQuantity() - checkItem.getQuantity();
			
			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));

			if (difference > 0) {
				product.removeStock(difference);
			} else if (difference < 0) {
				product.addStock(-difference); //tem que ser negativo para garantir que se torne positiva e aumente no estoque
			}

			productRepository.save(product);

			checkItem.setQuantity(item.getQuantity());
		}

		return repository.save(order);
	}

	public Order addNewItems(Long orderId, List<OrderItem> newItems) {
		Order order = repository.findById(orderId).orElseThrow(
				() -> new IllegalArgumentException("Pedido não encontrado"));

		if(order.getStatus() != OrderStatus.AGUARDANDO_PAGAMENTO){
			throw new IllegalArgumentException("Pagamento já efetuado, gerar um novo pedido");
		}

		for (OrderItem item : newItems) {
			OrderItem checkItem = order.getItems().stream()
					.filter(fi -> fi.getProduct().getId().equals(item.getProduct().getId()))
					.findFirst()
					.orElse(null);

			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));

			if (checkItem != null) {
				checkItem.setQuantity(checkItem.getQuantity() + item.getQuantity());

			} else {
				item.setProduct(product);
				item.setPrice(product.getPrice());
				item.setOrder(order); // Garante o vínculo entre eles
				itemRepository.save(item);

				order.getItems().add(item); // Adiciona o item na lista do pedido
			}

			product.removeStock(item.getQuantity());
			productRepository.save(product);
		}

		return repository.save(order);
	}
}
