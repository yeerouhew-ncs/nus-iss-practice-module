package tbs.tbsapi.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.manager.AuthManager;

@Log4j2
@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    AuthManager authManager;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid AddSubjectDto addSubjectDto) {
        log.info("START: REGISTER USER");
        return authManager.registerUser(addSubjectDto);
    }
}
