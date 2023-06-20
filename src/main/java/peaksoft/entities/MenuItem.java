package peaksoft.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(generator = "menu_item_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_item_gen", sequenceName = "menu_item_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;
    @JsonIgnore
    private Boolean InStock;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private StopList stopList;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Cheque> cheques;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private SubCategory subCategory;
}
