package tbs.tbsapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.controller.SubjectController;
import tbs.tbsapi.manager.SubjectManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectControllerTest {

    @Mock
    private SubjectManager subjectManager;

    @InjectMocks
    private SubjectController subjectController;

    @Test
    public void testGetSubjectDetails() {
        // Mocking the response from SubjectManager
        ResponseEntity<?> mockResponseEntity = ResponseEntity.status(HttpStatus.OK).body(Map.of("statusCode", "401", "message","FAILED TO GET USER DETAILS"));
        when(subjectManager.getSubjectDetails(anyString())).thenReturn(new ResponseEntity<>(mockResponseEntity.getStatusCode()));

        // Calling the controller method
        ResponseEntity<?> responseEntity = subjectController.getSubjectDetails("Bearer mockAccessToken");

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }
}

