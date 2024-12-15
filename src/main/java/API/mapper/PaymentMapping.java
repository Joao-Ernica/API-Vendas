package API.mapper;

import API.entites.Payment;
import API.entites.request.PaymentRequest;
import API.entites.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentMapping {
	
	private final ModelMapper mapper;

	public Payment toPayment(PaymentRequest request) {
		return mapper.map(request, Payment.class);
	}

	public PaymentResponse toPaymentResponse(Payment payment) {
		return mapper.map(payment, PaymentResponse.class);
	}

	public List<PaymentResponse> toPaymentResponseList(List<Payment> payment) {
		return payment.
				stream()
				.map(this::toPaymentResponse)
				.collect(Collectors.toList());
	}

}

