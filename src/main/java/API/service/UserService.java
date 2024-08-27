package API.service;

import API.entites.User;
import API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	public final UserRepository repository;

	public List<User> findAll(){
		return repository.findAll();
	}

	public User findByCPF(String CPF){
		return repository.findById(CPF).orElseThrow(()
				-> new IllegalArgumentException("CPF não encontrado"));
	}
}
