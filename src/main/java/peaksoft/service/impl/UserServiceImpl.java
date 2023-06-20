package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtService;
import peaksoft.dto.request.AcceptOrRejectRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exception.*;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;

    private User getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User email not found!"));
    }

    @PostConstruct
    public void saveAdmin() {
        User user = User.builder()
                .firstName("Admin")
                .lastName("Abdulhakimova")
                .dateOfBirth(LocalDate.parse("2005-06-21"))
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .phoneNumber("+996700875997")
                .role(Role.ADMIN)
                .accepted(true)
                .build();

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);

        }

    }

    private void registerUser(RegisterRequest registerRequest) {
        int age = LocalDate.now().minusYears(registerRequest.dateOfBirth().getYear()).getYear();
        if (registerRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                throw new NoValidException("Chef must be between 25 and 45 years of age");
            }
            if (registerRequest.experience() < 2) {
                throw new NoValidException("Chef experience must be more than 2 years");
            }
        } else if (registerRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                throw new NoValidException("Waiter must be between 18 and 30 years of age");
            }

            if (registerRequest.experience() < 1) {
                throw new NoValidException("Waiter experience must be more than 1 year");
            }
        }
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        if (userRepository.getAllUsers().size() == 15) {
            throw new NoVacancyException("There are currently no open vacancies");
        }
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new NotFoundException(String.format(
                    "User with login: %s is exists", registerRequest.email()
            ));
        }
        registerUser(registerRequest);
        User user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .dateOfBirth(registerRequest.dateOfBirth())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phoneNumber(registerRequest.phoneNumber())
                .role(registerRequest.role())
                .experience(registerRequest.experience())
                .accepted(false)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your application successfully sent!")
                .build();
    }

    @Override
    public UserTokenResponse authenticate(UserRequest userRequest) {
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
    public List<UserResponse> getApplications() {
        return userRepository.getAllApplication();
    }

    @Override
    public SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with ID: %s not found!", restaurantId)));
        if (restaurant.getUsers().size() >= 15) {
            throw new BadRequestException("The number of employees cannot be more than 15");
        }
        User user = userRepository.findById(acceptOrRejectRequest.getUserId()).orElseThrow(() ->
                new NotFoundException("User with id: " + acceptOrRejectRequest.getUserId() + " is not found!"));

        if (acceptOrRejectRequest.getAccept()) {
            user.setAccepted(true);
            user.setRestaurant(restaurant);
            restaurant.addUser(user);
            userRepository.save(user);

            return SimpleResponse.builder()
                    .status(HttpStatus.ACCEPTED)
                    .message(String.format("User %s is accepted!", user.getEmail()))
                    .build();

        } else {
            userRepository.delete(user);
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .message(String.format("User - %s is rejected!", user.getEmail()))
                    .build();
        }

    }

    @Override
    public SimpleResponse updateUser(Long userId, RegisterRequest request) {
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(getAuthenticate().getId()) && user.getEmail().equals(request.email())) {
                throw new NotFoundException("User with email: " + request.email() + " is exists");

            }
        }
        registerUser(request);
        User user = userRepository.findById(getAuthenticate().getId()).orElseThrow(() ->
                new NotFoundException(String.format("User with id: " + getAuthenticate().getId() + " is not found!")));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: " + getAuthenticate().getId() + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteByIdUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with Id: %s not found!", id)));

        Restaurant rest = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow();

        user.getCheques().forEach(cheque -> cheque.getMenuItems()
                .forEach(menuItem -> menuItem.setCheques(null)));
        userRepository.delete(user);
        rest.setNumberOfEmployees(rest.getNumberOfEmployees() - 1);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with ID: %s successfully deleted", id))
                .build();
    }

    @Override
    public UserResponse getById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " is not found!"));
    }
}