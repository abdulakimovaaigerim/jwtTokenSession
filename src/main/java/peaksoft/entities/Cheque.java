package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cheque {

    @Id
    @GeneratedValue(generator = "cheque_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen", sequenceName = "cheque_seq", allocationSize = 1)
    private Long id;
    private int priceAverage;
    private LocalDate createAt;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private User user;

    @ManyToMany(mappedBy = "cheques", cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private List<MenuItem> menuItems = new ArrayList<>();
}
