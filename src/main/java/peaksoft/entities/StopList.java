package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {

    @Id
    @GeneratedValue(generator = "stop_list_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stop_list_gen", sequenceName = "stop_list_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    private MenuItem menuItem;
}
