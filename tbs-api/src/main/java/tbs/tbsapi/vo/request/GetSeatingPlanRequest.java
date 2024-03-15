package tbs.tbsapi.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSeatingPlanRequest {
    private Integer planId;
    private Integer venueId;
}