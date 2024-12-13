package API.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "User_tb")
public class User implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.NONE)
	@Column(unique = true, nullable = false)
	private String cpf;

	private String name;
	private String email;
	private String phone;

	@Setter(AccessLevel.NONE)
	private String password;

	@OneToMany(mappedBy = "user")
	@Setter(AccessLevel.NONE)
	private Set<Order> order = new HashSet<>();
}
