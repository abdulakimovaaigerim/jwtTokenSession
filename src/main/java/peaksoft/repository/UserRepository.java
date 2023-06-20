package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.UserResponse;
import peaksoft.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new peaksoft.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.dateOfBirth, u.email, u.phoneNumber, u.role, u.experience) from User u")
    List<UserResponse> getAllUsers();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new peaksoft.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.dateOfBirth, u.email, u.phoneNumber, u.role, u.experience) from User u where u.accepted = false ")
    List<UserResponse> getAllApplication();

    @Query("select new peaksoft.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.dateOfBirth, u.email, u.phoneNumber, u.role, u.experience) from User u where u.id=:userId")
    Optional<UserResponse> getUserById(@Param("userId") Long userId);
}
