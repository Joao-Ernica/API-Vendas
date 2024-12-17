package API.entites;

import API.entites.pk.OrderItemPk;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

	@EmbeddedId
	@EqualsAndHashCode.Include
	@Getter(AccessLevel.NONE)
	private OrderItemPk id = new OrderItemPk();

	private Integer quantity;
	private BigDecimal price;

	private BigDecimal total;

	@Builder
	public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
		this.id.setOrder(order);
		this.id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

	public BigDecimal calculateTot(){ //utilizei BigDecimal por ser mais preciso e ja possuir os metodos necessarios
		return price.multiply(new BigDecimal(quantity));
	}

	@PostLoad //para Post
	@PreUpdate // para PUT
	@PrePersist // ativação inicial
	public void calculateTotal() {
		this.total = price.multiply(new BigDecimal(quantity));
	}

	public BigDecimal getTotal() { return price.multiply(new BigDecimal(quantity)); }

	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}

	@JsonIgnore
	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

}
