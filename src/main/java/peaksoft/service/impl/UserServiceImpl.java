package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtService;
import peaksoft.dto.request.AuthUserRequest;
import peaksoft.dto.request.SinUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.entiti.User;
import peaksoft.enums.Role;
import peaksoft.exception.ConflictException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostConstruct
    public void saveAdmin() {
        User user = User.builder()
                .firstName("Aigerim")
                .lastName("Bektenova")
                .email("aigerim@gmail.com")
                .password(passwordEncoder.encode("aigerim123"))
                .createDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .role(Role.ADMIN)
                .build();

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);

        }
    }

    @Override
    public UserTokenResponse authenticate(AuthUserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()));

        User user = userRepository.findByEmail(userRequest.email()).orElseThrow(() ->
                new NotFoundException(String.format("Admin with email: %s not found!", userRequest.email())));

        String token = jwtService.generateToken(user);

        return UserTokenResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse sinUp(SinUpRequest sinUpRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        sinUpRequest.email(),
                        sinUpRequest.password()));
        User user = userRepository.findByEmail(sinUpRequest.email()).orElseThrow(() ->
                new ConflictException("User with id: " + sinUpRequest.email() + " is not found!"));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder().
                email(user.getEmail())
                .token(token).
                build();
    }

    @Override
    public List<UserAllResponse> getAllUsers() {
        return userRepository.getAllUser();
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .createDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: " + userRequest.firstName() + " is saved!"))
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: " + id + " is not found!")));
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + "  is not found!"));

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setCreateDate(ZonedDateTime.now());
        user.setUpdateDate(ZonedDateTime.now());
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: " + id + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        userRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: " + id + " is deleted!"))
                .build();
    }

}
