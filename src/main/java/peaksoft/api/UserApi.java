package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import peaksoft.dto.request.AcceptOrRejectRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;


    @PostMapping("/login")
    public UserTokenResponse authentication(@RequestBody @Valid UserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @PostMapping("/register")
    public SimpleResponse sinIn(@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return userService.getAllUser();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/applications")
    public List<UserResponse> applications() {
        return userService.getApplications();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/accept/{restaurantId}")
    SimpleResponse acceptResponse(@PathVariable Long restaurantId,
                                  @RequestBody AcceptOrRejectRequest acceptOrRejectRequest) {
        return userService.acceptResponse(restaurantId, acceptOrRejectRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @PutMapping("/{userId}/update")
    SimpleResponse updateUser(@PathVariable Long userId,
                              @RequestBody RegisterRequest request) {
        return userService.updateUser(userId, request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/delete")
    SimpleResponse deleteUser(@PathVariable Long userId){
        return userService.deleteByIdUser(userId);
    }

}
