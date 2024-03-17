package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.SectionSeat;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSeatingPlanResponse {
    private Integer planId;
    private String planName;
    private Integer planRow;
    private Integer planCol;
    private Integer venueId;
    private String venueName;
    private String address;
    private List<SectionSeat> sectionSeatResponses;
}
