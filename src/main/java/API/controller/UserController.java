package API.controller;

import API.entites.User;
import API.entites.response.UserResponse;
import API.mapper.UserMapping;
import API.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

	// @Autowired  Melhor usar o final com o @RequiredArgsConstructor
	private final UserService service;

	private final UserMapping mapping;

	@GetMapping
	public List<UserResponse> findAll(){
		List<User> all = service.findAll();
		return mapping.toUserResponseList(all);

	}

	@GetMapping("{CPF}")
	public UserResponse findById(@PathVariable String CPF){
		User byCPF = service.findByCPF(CPF);
		return mapping.toUserResponse(byCPF);
	}
}
