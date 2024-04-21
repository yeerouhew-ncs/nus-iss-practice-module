package tbs.tbsapi.controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.manager.AuthManager;
import tbs.tbsapi.vo.request.LoginRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthManager authManager;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser() {
        when(authManager.authenticateUser(any(LoginRequest.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = authController.authenticateUser(new LoginRequest());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testRegister() {
        when(authManager.registerUser(any(AddSubjectDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = authController.register(new AddSubjectDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }
}
