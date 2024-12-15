package API.controller;

import API.entites.Payment;
import API.entites.request.PaymentRequest;
import API.entites.response.PaymentResponse;
import API.mapper.PaymentMapping;
import API.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

	@Autowired
	private final PaymentService service;

	@Autowired
	private final PaymentMapping mapping;

	@GetMapping
	public List<Payment> findAll(){
		return service.findAll();
	}

	@PutMapping("{orderId}")
	public PaymentResponse makePayment(@PathVariable Long orderId, @RequestBody PaymentRequest request){
		Payment payment = mapping.toPayment(request);
		Payment make = service.makePayment(payment, orderId);
		return mapping.toPaymentResponse(make);
	}

}
