package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.Category;

import java.util.List;


@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private List<String> images;
    private String characteristic;
    private Boolean isFavorite;
    private String madeline;
    private Category category;

}
