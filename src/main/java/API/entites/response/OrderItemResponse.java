package API.entites.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

	private ProductResponse product = getProduct();
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal total;
}
