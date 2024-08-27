package API.entites.request;

import API.entites.User;
import API.entites.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {
	private String userCpf;
	private final OrderStatus orderStatus = OrderStatus.AGUARDANDO_PAGAMENTO;
	private Set<OrderItemRequest> items;
}
