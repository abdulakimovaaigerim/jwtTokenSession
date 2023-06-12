package peaksoft.entiti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(generator = "basket_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "basket_gen", sequenceName = "basket_seq", allocationSize = 1)
    private Long id;
    @ManyToMany(mappedBy = "baskets", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.REFRESH})
    private List<Product> products;

    @OneToOne(cascade = {CascadeType.DETACH,
    CascadeType.PERSIST,
    CascadeType.REFRESH,
    CascadeType.MERGE})
    private User user;

    @Transient
    private Long userId;

    public void addProduct(Product product) {
        this.products.add(product);

    }
}
