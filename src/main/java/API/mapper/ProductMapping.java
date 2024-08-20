package API.mapper;

import API.entites.Product;
import API.entites.request.ProductRequest;
import API.entites.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapping {
	
	private final ModelMapper mapper;

	public Product toProduct(ProductRequest request) {
		return mapper.map(request, Product.class);
	}

	public ProductResponse toProductResponse(Product product) {
		return mapper.map(product, ProductResponse.class);
	}

	public List<ProductResponse> toProductResponseList(List<Product> product) {
		return product.
				stream()
				.map(this::toProductResponse)
				.collect(Collectors.toList());
	}

}

