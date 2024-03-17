package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.enums.SeatStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private Integer seatId;
    private String seatName;
    private SeatStatus seatStatus;
}
