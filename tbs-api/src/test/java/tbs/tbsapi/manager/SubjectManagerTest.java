package tbs.tbsapi.manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.service.UserDetailsServiceImpl;
import tbs.tbsapi.util.JwtUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SubjectManagerTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private SubjectManager subjectManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetSubjectDetails_Success() {
        String accessToken = "Bearer token";

        String email = "test@example.com";
        Subject subjectDetails = new Subject(); // Create subject details for testing

        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenReturn(email);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails); // Return null for UserDetails, as it's not relevant for this test
        when(userDetailsService.getSubjectDetails(email)).thenReturn(subjectDetails);

        ResponseEntity<?> response = subjectManager.getSubjectDetails(accessToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(subjectDetails, ((Map<?, ?>) response.getBody()).get("subjectDetails"));

        verify(jwtUtils).getUserNameFromJwtToken("token");
        verify(userDetailsService).loadUserByUsername(email);
        verify(userDetailsService).getSubjectDetails(email);
    }

    @Test
    void testGetSubjectDetails_Unauthorized() {
        String accessToken = "Bearer token";

        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenThrow(new BadCredentialsException("Invalid token"));

        ResponseEntity<?> response = subjectManager.getSubjectDetails(accessToken);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("401", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("FAILED TO GET USER DETAILS", ((Map<?, ?>) response.getBody()).get("message"));

        verify(jwtUtils).getUserNameFromJwtToken("token");
        verifyNoInteractions(userDetailsService); // Ensure userDetailsService methods are not invoked
    }
}
