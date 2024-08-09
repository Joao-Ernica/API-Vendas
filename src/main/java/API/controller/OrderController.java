package API.controller;

import API.entites.Order;
import API.entites.request.OrderRequest;
import API.entites.response.OrderResponse;
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
		Order insert = service.insert(order);
		return mapping.toOrderResponse(insert);
	}

	@PutMapping("{id}")
	public OrderResponse update(@PathVariable Long id, @RequestBody OrderRequest obj){
		Order order = mapping.toOrder(obj);
		Order update = service.update(id, order);
		return mapping.toOrderResponse(update);

	}

}
