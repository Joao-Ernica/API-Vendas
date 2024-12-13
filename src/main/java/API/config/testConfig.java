package API.config;

import API.entites.*;
import API.entites.enums.OrderStatus;
import API.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

@Configuration
@EnableJpaAuditing
@Profile("test")
public class testConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	@SneakyThrows
	public void run(String... args) {

		var u1 = User.builder()
				.Cpf("01234567890")
				.name("joao")
				.phone("12345678")
				.email("joao@hotmail.com")
				.password("Jp1234")
				.build();

		var u2 = User.builder()
				.Cpf("09876543210")
				.name("ana")
				.phone("87654321")
				.email("ana@hotmail.com")
				.password("ana1234")
				.build();

		/*
		As categorias dos livros estão apenas para fins de teste
		*/

		var cat = new Category(null, "Fantasia");
		var cat1 = new Category(null, "Aventura");
		var cat2 = new Category(null, "Ficção Científica");

		categoryRepository.saveAll(Arrays.asList(cat, cat1, cat2));

		var pr = Product.builder()
				.name("Trilogia, O Senhor dos Aneis")
				.description("Uma obra prima escrita por J.R.R Tolkien")
				.price(BigDecimal.valueOf(159.69))
				.stock(50)
				.categories(Set.of(cat, cat1))
				.build();

		var pr1 = Product.builder()
				.name("Harry Potter e a Pedra Filosofal")
				.description("Primeiro livro da série Harry Potter, escrito por J.K. Rowling")
				.price(BigDecimal.valueOf(49.90))
				.stock(50)
				.categories(Set.of(cat1))
				.build();

		var pr2 = Product.builder()
				.name("Duna")
				.description("Um clássico da ficção científica, escrito por Frank Herbert")
				.price(BigDecimal.valueOf(89.90))
				.stock(50)
				.categories(Set.of(cat2))
				.build();

		var pr3 = Product.builder()
				.name("O Hobbit")
				.description("Aventuras de Bilbo Bolseiro, escrito por J.R.R. Tolkien")
				.price(BigDecimal.valueOf(59.90))
				.stock(50)
				.categories(Set.of(cat2))
				.build();

		var pr4 = Product.builder()
				.name("As Crônicas de Nárnia")
				.description("Uma série de fantasia escrita por C.S. Lewis")
				.price(BigDecimal.valueOf(99.90))
				.stock(50)
				.categories(Set.of(cat1))
				.build();

// Produtos em duas categorias
		var pr5 = Product.builder()
				.name("O Nome do Vento")
				.description("Uma obra de fantasia e aventura escrita por Patrick Rothfuss")
				.price(BigDecimal.valueOf(59.90))
				.stock(50)
				.categories(Set.of(cat, cat1))
				.build();

		var pr6 = Product.builder()
				.name("Jogos Vorazes")
				.description("Uma série de ficção científica e aventura escrita por Suzanne Collins")
				.price(BigDecimal.valueOf(49.90))
				.stock(50)
				.categories(Set.of(cat1, cat2))
				.build();

		userRepository.saveAll(Arrays.asList(u1, u2));
		productRepository.saveAll(Arrays.asList(pr, pr1, pr2, pr3, pr4, pr5, pr6));

		var or = Order.builder().status(OrderStatus.AGUARDANDO_PAGAMENTO).user(u2).build();
		var or1 = Order.builder().status(OrderStatus.PAGAMENTO_CONFIRMADO).user(u1).build();
		var or2 = Order.builder().status(OrderStatus.EM_TRASPORTE).user(u2).build();
		var or3 = Order.builder().status(OrderStatus.ENTREGUE).user(u2).build();

		var oi = OrderItem.builder()
				.order(or)
				.product(pr)
				.quantity(1)
				.price(pr.getPrice()) // já retornando BigDecimal
				.build();

		var oi1 = OrderItem.builder()
				.order(or1)
				.product(pr2)
				.quantity(2)
				.price(pr2.getPrice()) // já retornando BigDecimal
				.build();

		var oi2 = OrderItem.builder()
				.order(or2)
				.product(pr1)
				.quantity(5)
				.price(pr1.getPrice()) // já retornando BigDecimal
				.build();

		var oi3 = OrderItem.builder()
				.order(or3)
				.product(pr3)
				.quantity(3)
				.price(pr3.getPrice()) // já retornando BigDecimal
				.build();

		var pay = Payment.builder().order(or).data(LocalDateTime.now()).build();
		var pay1 = Payment.builder().order(or1).data(LocalDateTime.now()).build();
		var pay2 = Payment.builder().order(or2).data(LocalDateTime.now()).build();
		var pay3 = Payment.builder().order(or3).data(LocalDateTime.now()).build();

		or.setPayment(pay);
		or1.setPayment(pay1);
		or2.setPayment(pay2);
		or3.setPayment(pay3);

		orderRepository.saveAll(Arrays.asList(or, or1, or2, or3));
		orderItemRepository.saveAll(Arrays.asList(oi, oi1, oi2, oi3));
		paymentRepository.saveAll(Arrays.asList(pay, pay1, pay2, pay3));

	}
}
