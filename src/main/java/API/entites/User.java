package API.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
	private String CPF;

	private String name;
	private String email;
	private String phone;

	@Setter(AccessLevel.NONE)
	private String password;

}
