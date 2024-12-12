package API.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Include
	private Long id;

	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime data;

	@OneToOne
	@MapsId // usar o mesmo id
	@JsonIgnore
	private Order order;

}
