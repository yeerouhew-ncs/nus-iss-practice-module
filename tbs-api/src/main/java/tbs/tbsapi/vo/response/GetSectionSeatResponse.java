package tbs.tbsapi.vo.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.Seat;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSectionSeatResponse {
    private Integer sectionId;
    private Integer totalSeats;
    private Integer noSeatsLeft;
    private Double seatPrice;
    private String seatSectionDescription;
    private Integer sectionRow;
    private Integer sectionCol;
    private List<Seat> seatResponses;
}
