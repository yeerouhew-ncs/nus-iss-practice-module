package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEventDto extends CreatedUpdatedColumn implements Serializable {
    @JsonProperty("eventName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String eventName;

    @JsonProperty("artistName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String artistName;

    @JsonProperty("eventFromDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventFromDt;

    @JsonProperty("eventToDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventToDt;

    @JsonProperty("planId")
    private Integer planId;

    @JsonProperty("subjectId")
    private Integer subjectId;

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AddEventDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<AddEventDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }
}
