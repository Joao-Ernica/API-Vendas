package API.controller;

import API.entites.Payment;
import API.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

	@Autowired
	private final PaymentService service;

	@GetMapping
	public List<Payment> findAll(){
		return service.findAll();
	}

}
