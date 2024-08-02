package API.mapper;

import API.entites.User;
import API.entites.request.UserRequest;
import API.entites.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapping  {

	private final ModelMapper mapper;

	public User toUser(UserRequest request) {
		return mapper.map(request, User.class);
	}

	public UserResponse toUserResponse(User user) {
		return mapper.map(user, UserResponse.class);
	}

	public List<UserResponse> toUserResponseList(List<User> user) {
		return user.
				stream()
				.map(this::toUserResponse)
				.collect(Collectors.toList());
	}

}
