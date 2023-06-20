package peaksoft.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptOrRejectRequest {
         private Long userId;
         private Boolean accept;
}
