package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditSeatDto extends SeatDto implements Serializable {
    @JsonProperty("seatId")
    private Integer seatId;

    @JsonProperty("seatStatus")
    private String seatStatus;
}
