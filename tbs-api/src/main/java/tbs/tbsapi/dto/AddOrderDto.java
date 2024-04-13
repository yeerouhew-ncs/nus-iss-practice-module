package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
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
public class AddOrderDto extends CreatedUpdatedColumn implements Serializable {
    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("seatNames")
    private List<String> seatNames;

    @JsonProperty("orderDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime orderDateTime;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("subjectId")
    private Integer subjectId;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AddOrderDto>> violations = validator.validate(this, Default.class);

        for (ConstraintViolation<AddOrderDto> violation : violations) {
            validationErrors.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return validationErrors;
    }
}
