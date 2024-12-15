package API.service;

import API.entites.Category;
import API.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

	@Autowired
	public final CategoryRepository repository;

	public List<Category> findAll(){
		return repository.findAll();

	}

	public Category findById(Long id){
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("id não encontrado"));

	}

	public Category insert(Category obj){
		return repository.save(obj);
	}

	public Category update(Category obj, Long id){
		Category referenceById = repository.getReferenceById(id);
		BeanUtils.copyProperties(obj, referenceById, "id");
		return repository.save(referenceById);
	}

}
