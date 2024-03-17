package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto implements Serializable {
    @JsonProperty("seatName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String seatName;
}
