package tbs.tbsapi.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.manager.AuthManager;

import tbs.tbsapi.vo.request.LoginRequest;

@Log4j2
@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    AuthManager authManager;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("START: LOGIN");
        return authManager.authenticateUser(loginRequest);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid AddSubjectDto addSubjectDto) {
        log.info("START: REGISTER USER");
        return authManager.registerUser(addSubjectDto);
    }
}
