package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionSeatResponse {
    private Integer sectionId;
    private Integer totalSeats;
    private Integer noSeatsLeft;
    private String sectionDesc;
    private Integer sectionRow;
    private Integer sectionCol;
    private Double seatPrice;
    private List<SeatResponse> seatList;
}
