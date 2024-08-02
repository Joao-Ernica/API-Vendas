package API.mapper;

import API.entites.Order;
import API.entites.request.OrderRequest;
import API.entites.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapping {
	
	private final ModelMapper mapper;

	public Order toOrder(OrderRequest request) {
		return mapper.map(request, Order.class);
	}

	public OrderResponse toOrderResponse(Order order) {
		return mapper.map(order, OrderResponse.class);
	}

	public List<OrderResponse> toOrderResponseList(List<Order> order) {
		return order.
				stream()
				.map(this::toOrderResponse)
				.collect(Collectors.toList());
	}

}

