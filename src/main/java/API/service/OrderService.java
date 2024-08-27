package API.service;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.Product;
import API.entites.User;
import API.entites.request.OrderItemRequest;
import API.entites.request.OrderRequest;
import API.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	public List<Order> findAll(){
		return repository.findAll();
	}

	public Order findById(Long id){
		return repository.findById(id).orElseThrow(()
				-> new IllegalArgumentException("Id não encontrado"));
	}

	public Order insert(OrderRequest request) {
		// Verificar se o usuário existe
		User user = userService.findByCPF(request.getUserCpf());

		// Criar o pedido
		Order order = new Order();
		order.setUser(user);
		order.setStatus(request.getOrderStatus());

		// Associar os itens ao pedido
		for (OrderItemRequest itemRequest : request.getItems()) {
			Product product = productService.findById(itemRequest.getProductId());
			BigDecimal price = BigDecimal.valueOf(product.getPrice());
			BigDecimal quantity = BigDecimal.valueOf(itemRequest.getQuantity());
			BigDecimal total = price.multiply(quantity);
			OrderItem item = new OrderItem(order, product, itemRequest.getQuantity(), price, total);
			order.addItem(item);
		}

		return repository.save(order);
	}

	public Order update(Long id, Order obj){
			Order byId = repository.findById(id).orElseThrow(()
					-> new IllegalArgumentException("Id não encontrado"));
			BeanUtils.copyProperties(obj, byId, "id");
			return repository.save(byId);
	}

}
