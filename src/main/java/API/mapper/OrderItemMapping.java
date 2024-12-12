package API.mapper;

import API.entites.OrderItem;
import API.entites.request.OrderItemRequest;
import API.entites.response.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemMapping {
	
	private final ModelMapper mapper;

	public OrderItem toOrderItem(OrderItemRequest request) {
		return mapper.map(request, OrderItem.class);
	}

	public List<OrderItem> toOrderItemList(List<OrderItemRequest> order) {
		return order.
				stream()
				.map(this::toOrderItem)
				.collect(Collectors.toList());
	}

	public OrderItemResponse toOrderItemResponse(OrderItem order) {
		return mapper.map(order, OrderItemResponse.class);
	}

	public List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> order) {
		return order.
				stream()
				.map(this::toOrderItemResponse)
				.collect(Collectors.toList());
	}

}

