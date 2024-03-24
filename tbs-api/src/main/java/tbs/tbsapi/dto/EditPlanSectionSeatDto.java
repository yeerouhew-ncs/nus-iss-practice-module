package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditPlanSectionSeatDto {
    @JsonProperty("planId")
    private Integer planId;

    @JsonProperty("sectionSeats")
    private List<EditSectionSeatDto> sectionSeats;
}
