package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.service.AuthService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.response.RegisterUserResponse;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AuthManager {
    @Autowired
    AuthService authService;

    public ResponseEntity<?> registerUser(AddSubjectDto addSubjectDto) {
        List<ValidationError> validationErrorList = addSubjectDto.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: REGISTER USER VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        RegisterUserResponse registerUserResponse = authService.registerUser(addSubjectDto);
        if(registerUserResponse.getSubjectId() != null) {
            log.info("END: REGISTER USER SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "SUCCESS",
                    "subjectDetails", registerUserResponse)
            );
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "statusCode", "409",
                "message", "FAILURE"));
    }
}
