package API.controller;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.request.OrderItemRequest;
import API.entites.request.OrderRequest;
import API.entites.response.OrderResponse;
import API.mapper.OrderItemMapping;
import API.mapper.OrderMapping;
import API.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

	@Autowired
	private final OrderMapping mapping;

	@Autowired
	private final OrderItemMapping orderItemMapping;

	@Autowired
	private final OrderService service;

	@GetMapping
	public List<OrderResponse> findAll(){
		List<Order> all = service.findAll();
		return mapping.toOrderResponseList(all);
	}

	@GetMapping("{id}")
	public OrderResponse findById(@PathVariable Long id){
		Order byId = service.findById(id);
		return mapping.toOrderResponse(byId);
	}

	@PostMapping
	public OrderResponse insert (@RequestBody OrderRequest request){
		Order order = mapping.toOrder(request);
		Order insert = service.insert(order, request.getUserCpf());
		return mapping.toOrderResponse(insert);
	}

	@PutMapping("{orderId}")
	public OrderResponse update(@PathVariable Long orderId, @RequestBody List<OrderItemRequest> updatedItem) {
		List<OrderItem> orderItems = orderItemMapping.toOrderItemList(updatedItem);
		Order updatedOrder = service.update(orderId, orderItems);
		return mapping.toOrderResponse(updatedOrder);
	}

	@PutMapping("{orderId}/addNewItems")
	public OrderResponse addNewItems(@PathVariable Long orderId, @RequestBody List<OrderItemRequest> newItems){
		List<OrderItem> orderItems = orderItemMapping.toOrderItemList(newItems);
		Order order = service.addNewItems(orderId, orderItems);
		return mapping.toOrderResponse(order);
	}

	@GetMapping("ByUserId/{cpf}")
	public List<OrderResponse> findByUserCpf(@PathVariable String cpf){
		List<Order> byUserCpf = service.findByUserCpf(cpf);
		return mapping.toOrderResponseList(byUserCpf);

	}
}
