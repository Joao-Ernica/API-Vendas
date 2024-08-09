package API.entites;

import API.entites.pk.OrderItemPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item_tb")
public class OrderItem implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Setter(AccessLevel.NONE)
	@EmbeddedId
	@EqualsAndHashCode.Include
	private OrderItemPk id = new OrderItemPk();

	private Integer quantity;
	private Double price;

	@Builder
	// até onde vi, o lombok não consegue colocar os ids no contrutor, por isso fiz manualmente
	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

}
