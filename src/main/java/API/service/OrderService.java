package API.service;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.Product;
import API.entites.User;
import API.entites.pk.OrderItemPk;
import API.entites.request.OrderItemRequest;
import API.entites.request.OrderRequest;
import API.repository.OrderItemRepository;
import API.repository.OrderRepository;
import API.repository.ProductRepository;
import API.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

//	public Order insert(OrderRequest orderRequest) {
//		// Buscar o usuário pelo CPF
//		String cpf = orderRequest.getUserCpf();
//		User user = userRepository.findById(cpf).orElseThrow(
//				() -> new IllegalArgumentException(" Usuário não encontrado"));
//
//		// Criar um novo pedido e associar o usuário
//		Order order = new Order();
//		order.setUser(user);
//
//		// Inicializar e adicionar os itens ao pedido
//		Set<OrderItem> items = orderRequest.getItems().stream().map(itemRequest -> {
//			Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(
//					() -> new IllegalArgumentException("Produto não existe"));
//
//			return OrderItem.builder()
//					.order(order)
//					.product(product)
//					.quantity(itemRequest.getQuantity())
//					.price(product.getPrice())
//					.build();
//		}).collect(Collectors.toSet());
//
//		// Associar os itens ao pedido
//		order.setItems(items);
//
//		// Salvar o pedido inicial para gerar o ID
//		Order savedOrder = repository.save(order);
//
//		// Salvar e atualizar itens do pedido com os dados do produto
//		savedOrder.getItems().forEach(item -> {
//			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
//					() -> new IllegalArgumentException("Produto não existe"));
//
//			item.setProduct(product);
//			item.setPrice(product.getPrice());
//			item.setOrder(savedOrder);
//			itemRepository.save(item);
//		});
//
//		// Retornar o pedido salvo e atualizado com os itens
//		return repository.save(savedOrder);
//	}

	public Order insert(OrderRequest orderRequest) {
		// Buscar o usuário pelo CPF
		User user = userRepository.findById(orderRequest.getUserCpf()).orElseThrow(()
				-> new IllegalArgumentException(orderRequest.getUserCpf() + " Usuário não encontrado"));

		// Criar um novo pedido e associar o usuário
		Order order = new Order();
		order.setUser(user);

		// Inicializar e adicionar os itens ao pedido
		Set<OrderItem> items = orderRequest.getItems().stream().map(itemRequest -> {
			Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(()
					-> new IllegalArgumentException("Produto não existe"));

			return OrderItem.builder()
					.order(order)
					.product(product)
					.quantity(itemRequest.getQuantity())
					.price(product.getPrice())
					.build();
		}).collect(Collectors.toSet());

		order.setItems(items);

		// Salvar o pedido e itens em uma única operação
		Order savedOrder = repository.save(order);
		savedOrder.getItems().forEach(item -> {
			Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não existe"));

			item.setProduct(product);
			item.setPrice(product.getPrice());
			item.setOrder(savedOrder);
		});

		itemRepository.saveAll(savedOrder.getItems());

		// Retornar o pedido salvo e atualizado com os itens
		return savedOrder;
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

		//verifica se existe produtos iguais
		for (OrderItem newItem : newItems) {
			OrderItem existingItem = order.getItems().stream()
					.filter(item -> item.getProduct().getId().equals(newItem.getProduct().getId()))
					.findFirst()
					.orElse(null);

			if (existingItem != null) {
				existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
			} else {
				Product product = productRepository.findById(newItem.getProduct().getId()).orElseThrow(
						() -> new IllegalArgumentException("Produto não existe"));

				newItem.setProduct(product);
				newItem.setPrice(product.getPrice());
				newItem.setOrder(order); // Garante o vínculo entre eles
				itemRepository.save(newItem);

				order.getItems().add(newItem); // Adiciona o item na lista do pedido
			}
		}

		return repository.save(order);
	}



}
