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
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.domain.enums.SubjectStatus;
import tbs.tbsapi.validation.EmailValidation;
import tbs.tbsapi.validation.ValidationError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSubjectDto implements Serializable {
    @JsonProperty("email")
    @EmailValidation
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String fullName;

    @JsonProperty("subjectRole")
    private SubjectRole subjectRole;

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AddSubjectDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<AddSubjectDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }
}
