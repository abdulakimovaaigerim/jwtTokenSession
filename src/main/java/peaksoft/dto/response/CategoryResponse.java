package peaksoft.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public record CategoryResponse (
     Long id,
     String name,
     List<SubCategoryResponse> subCategory ){

}
