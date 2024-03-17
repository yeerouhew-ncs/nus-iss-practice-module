package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.validation.ValidationError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditSeatingPlanDto implements Serializable {
    @JsonProperty("planId")
    private Integer planId;

    @JsonProperty("venueId")
    private Integer venueId;

    @JsonProperty("planName")
    private String planName;

    @JsonProperty("planRow")
    private Integer planRow;

    @JsonProperty("planCol")
    private Integer planCol;

    @JsonProperty("sectionSeats")
    private List<EditSectionSeatDto> sectionSeats;


    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EditSeatingPlanDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<EditSeatingPlanDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }

}
