package API.service;

import API.entites.Order;
import API.entites.OrderItem;
import API.entites.Payment;
import API.entites.enums.PaymentStatus;
import API.repository.OrderItemRepository;
import API.repository.OrderRepository;
import API.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

		if (order.getItems() == null || order.getItems().isEmpty()) {
			throw new IllegalArgumentException("Order não possui itens.");
		}

		List<OrderItem> orderItemsList = new ArrayList<>(order.getItems());
		OrderItem orderItem = orderItemsList.get(0); // Por exemplo, pegue o primeiro item

		BigDecimal totalOrderValue = orderItem.getTotal();
		if (totalOrderValue == null) {
			throw new IllegalArgumentException("O valor total do OrderItem não pode ser nulo.");
		}

		if (payment.getValor() == null) {
			throw new IllegalArgumentException("O valor do pagamento não pode ser nulo.");
		}

		if (totalOrderValue.compareTo(payment.getValor()) != 0) {
			throw new IllegalArgumentException("O valor do pagamento não corresponde ao valor do pedido"+ payment.getValor() +"  "+ orderItem.getTotal());
		}

		payment.setOrder(order);
		payment.setStatus(PaymentStatus.PAGAMENTO_CONFIRMADO);
		return repository.save(payment);
	}



}
