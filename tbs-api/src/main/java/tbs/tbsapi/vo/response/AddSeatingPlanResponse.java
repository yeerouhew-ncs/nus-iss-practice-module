package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSeatingPlanResponse {
    private String message;
    private String statusCode;
    private Integer planId;
    private Integer venueId;
    private List<SectionSeatResponse> sectionSeatResponseList;
}
