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

	/*
		//tentei de tudo, a unica solução que encotrei foi separar o Cpf ates de ir pro service
		ou mandar o OrderRequest para o service
	*/

	public Order insert(Order order, String userCpf) {
		User user = userRepository.findById(userCpf).orElseThrow(
				() -> new IllegalArgumentException(userCpf + " Usuário não encontrado"));

		//salvar primeiro para gerar o id do Order para os OrderItems
		order.setUser(user);
		Order save = repository.save(order);

		//.forEach deixa o codigo mais simples, não colocando diretamente o for
		save.getItems().forEach(item -> {

			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));
			
			product.removeStock(item.getQuantity());
			productRepository.save(product);

			item.setProduct(product);
			item.setPrice(product.getPrice());
			item.setOrder(save);
			itemRepository.save(item);
		});

		return repository.save(save);
	}

	public Order update(Long orderId, List<OrderItem> updatedItems) {
		Order order = repository.findById(orderId).orElseThrow(
				() -> new IllegalArgumentException("Pedido não encontrado"));

		if(order.getStatus() != OrderStatus.AGUARDANDO_PAGAMENTO){
			throw new IllegalArgumentException("Pagamento já efetuado, gerar um novo pedido");
		}

		for (OrderItem item : updatedItems) {
			OrderItem checkItem = order.getItems().stream()
					.filter(orderItem -> orderItem.getProduct().getId().equals(item.getProduct().getId()))
					.findFirst() //não precisa percorrer tudo já que existe apenas um Id para cada produto
					.orElseThrow(() -> new IllegalArgumentException("Item não encontrado no pedido"));

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

			if (checkItem != null) {
				checkItem.setQuantity(checkItem.getQuantity() + item.getQuantity());
			} else {
				Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
						() -> new IllegalArgumentException("Produto não existe"));
				
				product.removeStock(item.getQuantity());
				productRepository.save(product);
				
				item.setProduct(product);
				item.setPrice(product.getPrice());
				item.setOrder(order); // Garante o vínculo entre eles
				itemRepository.save(item);

				order.getItems().add(item); // Adiciona o item na lista do pedido
			}
		}

		return repository.save(order);
	}

}
