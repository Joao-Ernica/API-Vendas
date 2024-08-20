package API.mapper;

import API.entites.Category;
import API.entites.request.CategoryRequest;
import API.entites.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapping {
	
	private final ModelMapper mapper;

	public Category toCategory(CategoryRequest request) {
		return mapper.map(request, Category.class);
	}

	public CategoryResponse toCategoryResponse(Category category) {
		return mapper.map(category, CategoryResponse.class);
	}

	public List<CategoryResponse> toCategoryResponseList(List<Category> category) {
		return category.
				stream()
				.map(this::toCategoryResponse)
				.collect(Collectors.toList());
	}

}

