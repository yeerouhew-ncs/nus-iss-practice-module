package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.service.AuthService;
import tbs.tbsapi.service.UserDetailsImpl;
import tbs.tbsapi.util.JwtUtils;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.LoginRequest;
import tbs.tbsapi.vo.response.JwtResponse;
import tbs.tbsapi.vo.response.RegisterUserResponse;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AuthManager {
    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

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

        RegisterUserResponse userExistResponse = authService.getUserByEmail(addSubjectDto.getEmail());
        if(userExistResponse.getSubjectId() != null) {
            log.info("END: REGISTER FAILED DUE TO DUPLICATE EMAIL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "statusCode", "400",
                    "message", "EMAIL IN USE",
                    "subjectDetails", userExistResponse)
            );
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

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if(authentication.isAuthenticated()) {
                JwtResponse jwtResponse = JwtResponse.builder()
                        .accessToken(jwt)
//                        .email(userDetails.getUsername())
//                        .subjectId(userDetails.getId().toString())
                        .build();


                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "statusCode", "200",
                        "message", "SUCCESS",
                        "jwtDetails", jwtResponse
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "statusCode", "401",
                        "message", "FAILED TO SIGN IN"
                ));
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "WRONG CREDENTIALS"
            ));
        }
    }
}
