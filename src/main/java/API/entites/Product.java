package API.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@Table(name = "product_tb")
public class Product implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String name;
	private String description;
	private Double price;

	@ManyToMany
	private Set<Category> categories = new HashSet<>();

	@OneToMany(mappedBy = "id.product") //Jpa entende que deve usar o OrderItem como intermediario
	private Set<OrderItem> items = new HashSet<>();

	public Set<Order> getOrderns(){ //encontra os valores Order dentro do Orderitem e retorna quando é chamado
		Set<Order> set = new HashSet<>();
		for(OrderItem x : items){
			set.add(x.getOrder());
		}
		return set;
	}

}
