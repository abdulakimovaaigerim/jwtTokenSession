package peaksoft.entiti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(generator = "favorite_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen", sequenceName = "favorite_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Product product;

    @Transient
    private Long userId;

    @Transient
    private Long productId;
}
