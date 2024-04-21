package tbs.tbsapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.controller.VenueController;
import tbs.tbsapi.manager.VenueManager;
import tbs.tbsapi.vo.request.GetVenueRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VenueControllerTest {

    @Mock
    private VenueManager venueManager;

    @InjectMocks
    private VenueController venueController;

    @Test
    void testGetVenueDetails() {
        // Mocking the response from VenueManager
        ResponseEntity<?> mockResponseEntity = ResponseEntity.ok().build();
        when(venueManager.getVenueDetails(any(GetVenueRequest.class))).thenReturn(new ResponseEntity<>(mockResponseEntity.getStatusCode()));

        // Calling the controller method
        ResponseEntity<?> responseEntity = venueController.getVenueDetails(new GetVenueRequest());

        // Assertions
        assertEquals(mockResponseEntity, responseEntity);
    }

    @Test
    void testGetVenueList() {
        // Mocking the response from VenueManager
        ResponseEntity<?> mockResponseEntity = ResponseEntity.ok().build();
        when(venueManager.getVenueList()).thenReturn(new ResponseEntity<>(mockResponseEntity.getStatusCode()));

        // Calling the controller method
        ResponseEntity<?> responseEntity = venueController.getVenueList();

        // Assertions
        assertEquals(mockResponseEntity, responseEntity);
    }
}
