package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.manager.SubjectManager;
import tbs.tbsapi.service.UserDetailsServiceImpl;
import tbs.tbsapi.util.JwtUtils;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("api/subject")
@CrossOrigin(origins = "*")
public class SubjectController {

    @Autowired
    SubjectManager subjectManager;

    @PostMapping("/subject-details")
    public ResponseEntity<?> getSubjectDetails(@RequestHeader(name = "Authorization") String accessToken) {
        log.info("START: GET SUBJECT DETAILS");
        return subjectManager.getSubjectDetails(accessToken);
    }
}
