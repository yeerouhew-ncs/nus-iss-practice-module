package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.repository.VenueRepository;
import tbs.tbsapi.vo.request.GetVenueRequest;
import tbs.tbsapi.vo.response.GetVenueResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    void getVenueDetails_ReturnsCorrectVenueDetails() {
        GetVenueRequest venueRequest = new GetVenueRequest();
        venueRequest.setVenueId(123);

        Venue venue = new Venue();
        venue.setVenueId(123);
        venue.setVenueName("Test Venue");
        venue.setAddress("Test Address");

        when(venueRepository.findByVenueId(123)).thenReturn(venue);

        GetVenueResponse result = venueService.getVenueDetails(venueRequest);

        assertEquals(123, result.getVenueId());
        assertEquals("Test Venue", result.getVenueName());
        assertEquals("Test Address", result.getVenueAddress());
        verify(venueRepository, times(1)).findByVenueId(123);
    }

    @Test
    void getVenueList_ReturnsCorrectVenueList() {
        Venue venue1 = new Venue();
        venue1.setVenueId(1);
        venue1.setVenueName("Venue 1");
        venue1.setAddress("Address 1");

        Venue venue2 = new Venue();
        venue2.setVenueId(2);
        venue2.setVenueName("Venue 2");
        venue2.setAddress("Address 2");

        List<Venue> venueList = new ArrayList<>();
        venueList.add(venue1);
        venueList.add(venue2);

        when(venueRepository.findAll()).thenReturn(venueList);

        List<GetVenueResponse> resultList = venueService.getVenueList();

        assertEquals(2, resultList.size());
        assertEquals(1, resultList.get(0).getVenueId());
        assertEquals("Venue 1", resultList.get(0).getVenueName());
        assertEquals("Address 1", resultList.get(0).getVenueAddress());
        assertEquals(2, resultList.get(1).getVenueId());
        assertEquals("Venue 2", resultList.get(1).getVenueName());
        assertEquals("Address 2", resultList.get(1).getVenueAddress());
        verify(venueRepository, times(1)).findAll();
    }
}
