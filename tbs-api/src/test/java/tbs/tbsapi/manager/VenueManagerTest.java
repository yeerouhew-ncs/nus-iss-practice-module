package tbs.tbsapi.manager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tbs.tbsapi.service.VenueService;
import tbs.tbsapi.vo.request.GetVenueRequest;
import tbs.tbsapi.vo.response.GetVenueResponse;

public class VenueManagerTest {

    @Test
    public void testGetVenueDetails_NoMatchingVenue() {
        VenueService service = mock(VenueService.class);
        GetVenueRequest request = new GetVenueRequest();
        request.setVenueId(1);

        GetVenueResponse response = new GetVenueResponse(); // Empty response for no matching venue
        when(service.getVenueDetails(request)).thenReturn(response);

        VenueManager manager = new VenueManager();
        manager.venueService = service;

        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode", "200",
                "message", "NO MATCHING VENUE"
        ));

        ResponseEntity<?> actualResponse = manager.getVenueDetails(request);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(service, times(1)).getVenueDetails(request);
    }

    @Test
    void testGetVenueDetails_Success() {
        VenueService venueService = mock(VenueService.class);
        GetVenueRequest request = new GetVenueRequest();
        request.setVenueId(1);
        GetVenueResponse response = new GetVenueResponse();
        response.setVenueId(1);
        response.setVenueName("Test Venue");
        response.setVenueAddress("Test Address");
        when(venueService.getVenueDetails(request)).thenReturn(response);
        VenueManager venueManager = new VenueManager();
        venueManager.venueService = venueService;
        ResponseEntity<?> actualResponse = venueManager.getVenueDetails(request);
        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode", "200",
                "message", "SUCCESS",
                "venueDetails", response
        ));
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(venueService, times(1)).getVenueDetails(request);
    }

    @Test
    public void testGetVenueList_Success() {
        VenueService service = mock(VenueService.class);
        List<GetVenueResponse> venueResponseList = new ArrayList<>();
        GetVenueResponse venueResponse = new GetVenueResponse();
        venueResponse.setVenueId(1);
        venueResponse.setVenueName("Test Venue");
        venueResponse.setVenueAddress("Test Address");
        venueResponseList.add(venueResponse);
        when(service.getVenueList()).thenReturn(venueResponseList);

        VenueManager manager = new VenueManager();
        manager.venueService = service;

        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode", "200",
                "message", "SUCCESS",
                "venueList", venueResponseList
        ));

        ResponseEntity<?> actualResponse = manager.getVenueList();

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(service, times(1)).getVenueList();
    }
}
