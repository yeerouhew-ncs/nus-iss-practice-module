package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.service.UserDetailsServiceImpl;
import tbs.tbsapi.util.JwtUtils;

import java.util.Map;

@Log4j2
@Service
public class SubjectManager {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    public ResponseEntity<?> getSubjectDetails(String accessToken) {
        String token = accessToken.substring(7);
        try{
            String email = jwtUtils.getUserNameFromJwtToken(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            Subject subjectDetails = userDetailsService.getSubjectDetails(email);

            log.info("userDetails: {} ", userDetails);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "SUCCESS",
                    "userDetails", userDetails,
                    "subjectDetails", subjectDetails
            ));
        } catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "statusCode", "401",
                    "message", "FAILED TO GET USER DETAILS"
            ));
        }
    }
}
