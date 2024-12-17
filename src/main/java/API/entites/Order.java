package API.entites;

import API.entites.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)//necessario para as criar datas
@Table(name = "Order_tb")
public class Order implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Include
	private Long id;

	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(updatable = false) // não pode ser alterado  apos a criação
	private LocalDate date;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "user_CPF")
	private User user;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Payment payment;

	private BigDecimal total;

	@OneToMany(mappedBy = "id.order")
	@Builder.Default //Lombok não inicializa construtores com coleções, preciso disso para o metodo upadateTotal
	private Set<OrderItem> items = new HashSet<>();

	@PostLoad
	@PreUpdate
	@PrePersist
	public void updateTotal() {
		this.total = items.stream()
				.map(OrderItem::getTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

//	public void addItem(OrderItem item) {
//		items.add(item);
//		item.setOrder(this);
//	}
//
//	public void addItems(List<OrderItem> newItems) {
//		for (OrderItem item : newItems) {
//			if (!items.contains(item)) {
//				item.setOrder(this);
//				addItem(item);
//			}
//		}
//	}
}

