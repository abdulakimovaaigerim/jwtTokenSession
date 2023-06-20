package peaksoft.service;


import peaksoft.dto.request.AcceptOrRejectRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.UserTokenResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();

    SimpleResponse register(RegisterRequest registerRequest);

    UserTokenResponse authenticate(UserRequest userRequest);

    List<UserResponse> getApplications();

    SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest);

    SimpleResponse updateUser(Long userId, RegisterRequest request);

    SimpleResponse deleteByIdUser(Long id);

    UserResponse getById(Long userId);
}
