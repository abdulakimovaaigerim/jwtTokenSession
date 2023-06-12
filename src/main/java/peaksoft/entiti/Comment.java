package peaksoft.entiti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;
    private String comment;
    private LocalDate createAt;

    @ManyToOne(cascade = { CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private Product product;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private User user;

}
