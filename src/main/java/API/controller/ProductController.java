package API.controller;

import API.entites.Product;
import API.entites.request.ProductRequest;
import API.entites.response.ProductResponse;
import API.mapper.ProductMapping;
import API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductMapping mapping;

	private ProductService service;

	@GetMapping
	public List<ProductResponse> findAll(){
		return mapping.toProductResponseList(service.findAll());
	}

	@GetMapping("{id}")
	public ProductResponse findById(@PathVariable Long id){
		Product byId = service.findById(id);
		return mapping.toProductResponse(byId);

	}

	@PostMapping("{id}")
	public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest obj){
		Product product = mapping.toProduct(obj);
		Product upadate = service.upadate(id,product);
		return mapping.toProductResponse(upadate);

	}

}
