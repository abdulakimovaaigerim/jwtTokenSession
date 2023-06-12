package peaksoft.service;

import peaksoft.dto.request.AuthUserRequest;
import peaksoft.dto.request.SinUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;

import java.util.List;

public interface UserService {

    UserTokenResponse authenticate(AuthUserRequest userRequest);

    AuthResponse sinUp(SinUpRequest sinUpRequest);

    List<UserAllResponse> getAllUsers();

    SimpleResponse saveUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    SimpleResponse updateUser(Long id, UserRequest userRequest);

    SimpleResponse deleteById(Long id);
}
