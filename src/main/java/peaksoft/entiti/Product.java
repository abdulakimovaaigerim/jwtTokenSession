package peaksoft.entiti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.Category;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "product_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ElementCollection
    private List<String> images;
    private String characteristic;
    private Boolean isFavorite;
    private String madeline;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE})
    private List<Basket> baskets = new ArrayList<>();

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private List<Favorite> favorites = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addBasket(Basket basket) {
        this.baskets.add(basket);
    }
}
