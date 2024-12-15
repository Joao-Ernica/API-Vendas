package API.entites;

import API.entites.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Payment_tb")
public class Payment implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Include
	private Long id;

	private BigDecimal valor;

	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime data;

	@OneToOne
	@MapsId // usar o mesmo id
	@JsonIgnore
	private Order order;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

}
