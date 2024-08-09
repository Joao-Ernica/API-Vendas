package API.service;

import API.entites.Order;
import API.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	public List<Order> findAll(){
		return repository.findAll();
	}

	public Order findById(Long id){
		return repository.findById(id).orElseThrow(()
				-> new IllegalArgumentException("Id não encontrado"));
	}

	public Order insert(Order obj){
		return repository.save(obj);
	}

	public Order update(Long id, Order obj){
			Order byId = repository.findById(id).orElseThrow(()
					-> new IllegalArgumentException("Id não encontrado"));
			BeanUtils.copyProperties(obj, byId, "id");
			return repository.save(byId);
	}

}
