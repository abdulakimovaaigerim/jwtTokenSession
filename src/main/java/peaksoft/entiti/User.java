package peaksoft.entiti;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createDate;
    private ZonedDateTime updateDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user", cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
    CascadeType.REMOVE})
    private Basket basket;
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    private List<Favorite> favorites = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password, ZonedDateTime createDate, ZonedDateTime updateDate, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }
}