package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserAllResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entiti.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    @Query("select new peaksoft.dto.response.UserAllResponse(u.id, u.firstName, u.lastName, u.email, u.createDate, u.updateDate, u.role) from User u")
    List<UserAllResponse> getAllUser();
    @Query("select new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.email,u.createDate,u.updateDate,u.role) from User u where u.id=:id")
    Optional<UserResponse> getUserById(Long id);


}
