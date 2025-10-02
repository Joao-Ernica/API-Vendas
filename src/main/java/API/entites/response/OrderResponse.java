package API.entites.response;

import API.entites.Payment;
import API.entites.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {

	private Long id;
	private LocalDate date;
	private OrderStatus status;
	private Payment payment;
	private UserResponse user;
	private Set<OrderItemResponse> items;
}
