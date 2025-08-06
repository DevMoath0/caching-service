package sa.com.moath.caching.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sa.com.moath.openapi.server.api.DefaultApi;
import sa.com.moath.openapi.server.model.CreateUserResponse;
import sa.com.moath.openapi.server.model.UserRequest;
import sa.com.moath.openapi.server.model.UserResponse;
import sa.com.moath.caching.service.impl.UsersServiceImpl;

@RestController
@RequiredArgsConstructor
public class UsersController implements DefaultApi {

    private final UsersServiceImpl usersService;

    @Override
    public ResponseEntity<UserResponse> userGet(String id) {
        return ResponseEntity.ok(usersService.getUser(Long.parseLong(id)));
    }

    @Override
    public ResponseEntity<CreateUserResponse> userPost(UserRequest userRequest) {
        return ResponseEntity.ok(usersService.createUser(userRequest));
    }
}
