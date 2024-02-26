package tbs.tbsapi.validation;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String errorMsg;

    public ValidationError(String field, String errorMsg) {
        this.field = field;
        this.errorMsg = errorMsg;}

    public ValidationError() {

    }
}
