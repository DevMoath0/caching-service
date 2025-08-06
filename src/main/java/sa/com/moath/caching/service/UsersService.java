package sa.com.moath.caching.service;

import sa.com.moath.openapi.server.model.CreateUserResponse;
import sa.com.moath.openapi.server.model.UserRequest;
import sa.com.moath.openapi.server.model.UserResponse;

public interface UsersService {

    UserResponse getUser(Long id);

    CreateUserResponse createUser(UserRequest userRequest);

}
