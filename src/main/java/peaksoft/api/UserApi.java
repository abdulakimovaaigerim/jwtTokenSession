package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AuthUserRequest;
import peaksoft.dto.request.SinUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody @Valid AuthUserRequest authUserRequest) {
        return userService.authenticate(authUserRequest);
    }

    @PostMapping("/sinUp")
    public AuthResponse sinUp(@RequestBody @Valid SinUpRequest sinUpRequest) {
        return userService.sinUp(sinUpRequest);
    }

    @GetMapping
    public List<UserAllResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/save")
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
