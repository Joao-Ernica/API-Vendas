package API.controller;

import API.entites.Category;
import API.entites.request.CategoryRequest;
import API.entites.response.CategoryResponse;
import API.mapper.CategoryMapping;
import API.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

	// @Autowired  Melhor usar o final com o @RequiredArgsConstructor
	private final CategoryService service;

	private final CategoryMapping mapping;

	@GetMapping
	public List<CategoryResponse> findAll() {
		List<Category> all = service.findAll();
		return mapping.toCategoryResponseList(all);
	}

	@GetMapping({"id"})
	public CategoryResponse findById(@PathVariable Long id) {
		Category byId = service.findById(id);
		return mapping.toCategoryResponse(byId);
	}

	@PostMapping
	public CategoryResponse insert(CategoryRequest obj) {
		Category category = mapping.toCategory(obj);
		Category insert = service.insert(category);
		return mapping.toCategoryResponse(insert);
	}

	public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request) {
		Category category = mapping.toCategory(request);
		Category update = service.update(category, id);
		return mapping.toCategoryResponse(update);

	}

}
