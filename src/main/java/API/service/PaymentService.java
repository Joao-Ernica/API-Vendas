package API.service;

import API.entites.Order;
import API.entites.Payment;
import API.entites.enums.PaymentStatus;
import API.repository.OrderItemRepository;
import API.repository.OrderRepository;
import API.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service

public class PaymentService {

	@Autowired
	private PaymentRepository repository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	public List<Payment> findAll(){
		return repository.findAll();
	}

	public Payment makePayment(Payment payment, Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new IllegalArgumentException("Order não encontrada"));

		BigDecimal totalOrderValue = order.calculateTotal();
		if (totalOrderValue == null) {
			throw new IllegalArgumentException("O valor total do pedido não pode ser nulo.");
		}

		if (payment.getAmountPaid() == null) {
			throw new IllegalArgumentException("O valor do pagamento não pode ser nulo.");
		}

		if (totalOrderValue.compareTo(payment.getAmountPaid()) != 0) {
			throw new IllegalArgumentException("O valor do pagamento não corresponde ao valor total do pedido: "
					+ payment.getAmountPaid() + "  " + totalOrderValue);
		}

		payment.setOrder(order);
		payment.setStatus(PaymentStatus.PAGAMENTO_CONFIRMADO); // fazer que toda fez que atualizar aqui, atualizar o status do order
		return repository.save(payment);
	}

}
