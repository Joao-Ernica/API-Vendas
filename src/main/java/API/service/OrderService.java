package API.service;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.Product;
import API.entites.User;
import API.repository.OrderItemRepository;
import API.repository.OrderRepository;
import API.repository.ProductRepository;
import API.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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

		//salvar primeiro para gerar o id do Order para os OrderItems		order.setUser(user);
		Order save = repository.save(order);

		//.forEach deixa o codigo mais simples, não colocando diretamente o for
		save.getItems().forEach(item -> {
			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));

			item.setProduct(product);
			item.setPrice(product.getPrice());
			item.setOrder(save);
			itemRepository.save(item);
		});

		return repository.save(save);
	}

	public Order update(Long id, Order obj){
			Order byId = repository.findById(id).orElseThrow(()
					-> new IllegalArgumentException("Pedido não encontrado"));
			BeanUtils.copyProperties(obj, byId, "id");
			return repository.save(byId);
	}

	public Order addNewItems(Long orderId, List<OrderItem> newItems) {
		Order order = repository.findById(orderId).orElseThrow(
				() -> new IllegalArgumentException("Pedido não encontrado"));


		for (OrderItem Item : newItems) {
			OrderItem existingItem = order.getItems().stream()
					.filter(item -> item.getProduct().getId().equals(Item.getProduct().getId()))
					.findFirst()
					.orElse(null);

			if (existingItem != null) {
				existingItem.setQuantity(existingItem.getQuantity() + Item.getQuantity());
			} else {
				Product product = productRepository.findById(Item.getProduct().getId()).orElseThrow(
						() -> new IllegalArgumentException("Produto não existe"));

				Item.setProduct(product);
				Item.setPrice(product.getPrice());
				Item.setOrder(order); // Garante o vínculo entre eles
				itemRepository.save(Item);

				order.getItems().add(Item); // Adiciona o item na lista do pedido
			}
		}

		return repository.save(order);
	}
}
