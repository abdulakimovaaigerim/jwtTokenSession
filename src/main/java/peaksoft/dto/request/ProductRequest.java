package peaksoft.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductRequest(
//        @NotBlank(message = "name cannot be empty!")
        String name,
//        @NotBlank(message = "images cannot be empty!")
        List<String> images,
//        @NotBlank(message = "characteristic cannot be empty!")
        String characteristic,


        Boolean isFavorite,
//        @NotBlank(message = "madeLine cannot be empty!")
        String madeline,
//        @NotBlank(message = "category cannot be empty!")
        Category category) {
}
