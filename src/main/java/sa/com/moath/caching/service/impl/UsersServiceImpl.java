package sa.com.moath.caching.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sa.com.moath.openapi.server.model.CreateUserResponse;
import sa.com.moath.openapi.server.model.UserRequest;
import sa.com.moath.openapi.server.model.UserResponse;
import sa.com.moath.caching.common.constant.CachingError;
import sa.com.moath.caching.configuration.ContextHolder;
import sa.com.moath.caching.configuration.RequestContext;
import sa.com.moath.caching.exception.CachingManagementBusinessException;
import sa.com.moath.caching.model.entity.Users;
import sa.com.moath.caching.repository.UsersRepository;
import sa.com.moath.caching.service.UsersService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public CreateUserResponse createUser(UserRequest userRequest) {
        validateUser();

        Users user = new Users();
        user.setName(userRequest.getName());
        user.setAge(Long.valueOf(userRequest.getAge()));
        user.setMobileNumber(userRequest.getMobileNumber());
        user.setEmail(userRequest.getEmail());

        usersRepository.save(user);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(String.valueOf(user.getId()));
        createUserResponse.setName(user.getName());
        createUserResponse.setMobileNumber(user.getMobileNumber());

        return createUserResponse;
    }

    @Override
    @Cacheable(
            value = "userDetails",
            key = "T(sa.com.moath.caching.common.util.CacheKeyGenerator).generateKey('Details', #id)",
            unless = "#result?.id == null")
    public UserResponse getUser(Long id) {
        validateUser();

        Users user = usersRepository.findUsersById(id).orElseThrow(() -> {
                    log.error("[getUser] User not found for userId '{}'", id);
                    return new CachingManagementBusinessException(
                            CachingError.USER_NOT_FOUND.getMessage(),
                            CachingError.USER_NOT_FOUND.getCode()
                    );
                }
        );

        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setName(user.getName());
        userResponse.setAge(Math.toIntExact(user.getAge()));
        userResponse.setMobileNumber(user.getMobileNumber());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    private void validateUser(){
        RequestContext requestContext = ContextHolder.get();
        if(requestContext.getUserId() == null || requestContext.getUserId().isBlank()) {
            throw new CachingManagementBusinessException(
                    CachingError.USER_NOT_FOUND.getMessage(),
                    CachingError.USER_NOT_FOUND.getCode()
            );
        }
    }

}
