package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionSeatDto implements Serializable {
    @JsonProperty("totalSeats")
    private Integer totalSeats;

    @JsonProperty("sectionDesc")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String sectionDesc;

    @JsonProperty("sectionRow")
    private Integer sectionRow;

    @JsonProperty("sectionCol")
    private Integer sectionCol;

    @JsonProperty("seatPrice")
    private Double seatPrice;

    @JsonProperty("seats")
    private List<SeatDto> seats;
}
