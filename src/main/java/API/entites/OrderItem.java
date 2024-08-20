package API.entites;

import API.entites.pk.OrderItemPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

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
	private BigDecimal price;
	private BigDecimal total;

	@Builder
	// até onde vi, o lombok não consegue colocar os ids no contrutor, por isso fiz manualmente
	public OrderItem(Order order, Product product, Integer quantity, BigDecimal price, BigDecimal total) {
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
		this.total = calculoTotal();
	}

	public BigDecimal calculoTotal(){
		//utilizei BigDecimal por ser mais preciso e ja possuir os metodos necessarios
		return price.multiply(new BigDecimal(quantity));
	}

}
