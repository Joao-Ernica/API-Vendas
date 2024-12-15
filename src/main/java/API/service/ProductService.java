package API.service;

import API.entites.Product;
import API.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Id não encotrado"));
	}

	public Product insert(Product obj) {
		return repository.save(obj);
	}

	public Product upadate(Long id, Product obj){
		Product byId = repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Id não encontrado"));
		BeanUtils.copyProperties(obj, byId, "id");
		return repository.save(byId);
	}
}
