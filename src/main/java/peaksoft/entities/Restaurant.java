package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(generator = "restaurant_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "restaurant_gen", sequenceName = "restaurant_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int service;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
    }
}
