package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.CreatedUpdatedColumn;
import tbs.tbsapi.validation.ValidationError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSeatingPlanDto extends CreatedUpdatedColumn implements Serializable {
    @JsonProperty("venueId")
    private Integer venueId;

    @JsonProperty("planName")
    private String planName;

    @JsonProperty("planRow")
    private Integer planRow;

    @JsonProperty("planCol")
    private Integer planCol;

    @JsonProperty("sectionSeats")
    private List<SectionSeatDto> sectionSeats;

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AddSeatingPlanDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<AddSeatingPlanDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }

}
