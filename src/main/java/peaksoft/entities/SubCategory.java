package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {

    @Id
    @GeneratedValue(generator = "sub_category_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_category_gen", sequenceName = "sub_category_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;
}
