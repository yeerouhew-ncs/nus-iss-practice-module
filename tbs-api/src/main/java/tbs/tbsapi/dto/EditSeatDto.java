package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.enums.SeatStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditSeatDto implements Serializable {
    @JsonProperty("seatId")
    private Integer seatId;

    @JsonProperty("seatName")
    private String seatName;

    @JsonProperty("seatRow")
    private Integer seatRow;

    @JsonProperty("seatCol")
    private Integer seatCol;

    @JsonProperty("seatStatus")
    private SeatStatus seatStatus;
}
