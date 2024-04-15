package tbs.tbsapi.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.repository.SubjectRepository;
import tbs.tbsapi.service.AuthService;
import tbs.tbsapi.service.AuthServiceImpl;
import tbs.tbsapi.service.UserDetailsImpl;
import tbs.tbsapi.util.JwtUtils;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.LoginRequest;
import tbs.tbsapi.vo.response.RegisterUserResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthManagerTest {


    @Test
    void testRegisterUser_ValidationFailed() {
        // Given
        AddSubjectDto addSubjectDto = mock(AddSubjectDto.class);
        // Setup addSubjectDto properties

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("email", "Email is required"));
        validationErrors.add(new ValidationError("password", "Password is required"));

        when(addSubjectDto.validate()).thenReturn(validationErrors);

        AuthManager authManager = new AuthManager();
        ResponseEntity<?> responseEntity = authManager.registerUser(addSubjectDto);

        // Then
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals("422", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) responseEntity.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) responseEntity.getBody()).get("validationError"));
    }

    @Test
    void testRegisterUser_EmailInUse() {
        // Given
        AddSubjectDto addSubjectDto = new AddSubjectDto();
        addSubjectDto.setEmail("test@test.com");
        addSubjectDto.setPassword("Pass1234");

        RegisterUserResponse userExistResponse = new RegisterUserResponse();
        userExistResponse.setSubjectId(1);
        AuthService authService = mock(AuthService.class);
        when(authService.getUserByEmail(addSubjectDto.getEmail())).thenReturn(userExistResponse);

        AuthManager authManager = new AuthManager();
        authManager.authService = authService;
        ResponseEntity<?> responseEntity = authManager.registerUser(addSubjectDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("400", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
        assertEquals("EMAIL IN USE", ((Map<?, ?>) responseEntity.getBody()).get("message"));
        assertEquals(userExistResponse, ((Map<?, ?>) responseEntity.getBody()).get("subjectDetails"));
    }

    @Test
    void testRegisterUser_Success() {

        AuthService authService = mock(AuthService.class);
        AddSubjectDto addSubjectDto = mock(AddSubjectDto.class);
        addSubjectDto.setEmail("test@email.com");
        RegisterUserResponse r = new RegisterUserResponse();
        r.setSubjectId(null);
        when(authService.getUserByEmail(addSubjectDto.getEmail())).thenReturn(new RegisterUserResponse());

        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setSubjectId(1);
        when(authService.registerUser(addSubjectDto))
                .thenReturn(registerUserResponse);

        AuthManager authManager = new AuthManager();
        authManager.authService = authService;
        ResponseEntity<?> responseEntity = authManager.registerUser(addSubjectDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("200", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) responseEntity.getBody()).get("message"));
        assertEquals(registerUserResponse, ((Map<?, ?>) responseEntity.getBody()).get("subjectDetails"));
    }

    @Test
    void testRegisterUser_Failure() {
        // Given
        AddSubjectDto addSubjectDto = new AddSubjectDto();
        addSubjectDto.setEmail("test@test.com");
        AuthService authService = mock(AuthService.class);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setSubjectId(null);
        when(authService.getUserByEmail(addSubjectDto.getEmail()))
                .thenReturn(registerUserResponse);

        when(authService.registerUser(addSubjectDto))
                .thenReturn(registerUserResponse);

        AuthManager authManager = new AuthManager();
        authManager.authService = authService;
        ResponseEntity<?> responseEntity = authManager.registerUser(addSubjectDto);

        // Then
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("409", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
        assertEquals("FAILURE", ((Map<?, ?>) responseEntity.getBody()).get("message"));
    }

    @Test
    void testAuthenticateUser_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtils jwtUtils = mock(JwtUtils.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any())).thenReturn("mockedJWTToken");
        when(authentication.isAuthenticated()).thenReturn(true);
        AuthManager authManager = new AuthManager();
        authManager.authenticationManager = authenticationManager;
        authManager.jwtUtils = jwtUtils;
        ResponseEntity<?> responseEntity = authManager.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testAuthenticateUser_FailToSignIn() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(false);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtils jwtUtils = mock(JwtUtils.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        AuthManager authManager = new AuthManager();
        authManager.authenticationManager = authenticationManager;
        authManager.jwtUtils = jwtUtils;

        ResponseEntity<?> responseEntity = authManager.authenticateUser(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("FAILED TO SIGN IN", ((Map<?, ?>) responseEntity.getBody()).get("message"));
        assertEquals("401", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
    }

    @Test
    void testAuthenticateUser_WrongCredentials() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongpassword");

        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtils jwtUtils = mock(JwtUtils.class);

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        AuthManager authManager = new AuthManager();
        authManager.authenticationManager = authenticationManager;
        authManager.jwtUtils = jwtUtils;

        ResponseEntity<?> responseEntity = authManager.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("WRONG CREDENTIALS", ((Map<?, ?>) responseEntity.getBody()).get("message"));
        assertEquals("200", ((Map<?, ?>) responseEntity.getBody()).get("statusCode"));
    }


}
