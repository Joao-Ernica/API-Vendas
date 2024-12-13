package API.service;

import API.entites.Payment;
import API.repository.PaymentRepository;
import API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PaymentService {

	@Autowired
	private PaymentRepository repository;

	public List<Payment> findAll(){
		return repository.findAll();
	}
}
