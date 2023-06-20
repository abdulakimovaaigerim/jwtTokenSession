package peaksoft.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;
}

