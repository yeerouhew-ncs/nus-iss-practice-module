package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class EditEventDto extends AddEventDto implements Serializable {
    @JsonProperty("eventId")
    private Integer eventId;

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EditEventDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<EditEventDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }
}
