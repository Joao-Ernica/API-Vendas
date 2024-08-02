package API.config;

import API.entites.User;
import API.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@Configuration
@EnableJpaAuditing
@Profile("test")
public class testConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	@SneakyThrows
	public void run(String... args) {

		var u1 = User.builder()
				.CPF("01234567890")
				.name("joao")
				.phone("12345678")
				.email("joao@hotmail.com")
				.password("Jp1234")
				.build();

		var u2 = User.builder()
				.CPF("09876543210")
				.name("ana")
				.phone("87654321")
				.email("ana@hotmail.com")
				.password("ana1234")
				.build();

		userRepository.saveAll(Arrays.asList(u1, u2));
	}
}
