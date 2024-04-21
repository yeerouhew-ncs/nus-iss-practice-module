package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.repository.VenueRepository;
import tbs.tbsapi.vo.request.GetVenueRequest;
import tbs.tbsapi.vo.request.SearchVenueRequest;
import tbs.tbsapi.vo.response.GetVenueResponse;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    VenueRepository venueRepository;
    public GetVenueResponse getVenueDetails(GetVenueRequest venueRequest) {
        Venue venue = venueRepository.findByVenueId(venueRequest.getVenueId());

        GetVenueResponse venueResponse = new GetVenueResponse();
        venueResponse.setVenueId(venue.getVenueId());
        venueResponse.setVenueName(venue.getVenueName());
        venueResponse.setVenueAddress(venue.getAddress());

        return venueResponse;
    }
    public List<GetVenueResponse> searchVenue(SearchVenueRequest venueRequest) {
        return venueRepository.searchBy(venueRequest.getAddress(),venueRequest.getVenueName());
    }

    public List<GetVenueResponse> getVenueList() {
        List<Venue> venueList = venueRepository.findAll();

        List<GetVenueResponse> venueResponseList = new ArrayList<>();
        for(Venue venue : venueList) {
            GetVenueResponse venueResponse = new GetVenueResponse();
            venueResponse.setVenueId(venue.getVenueId());
            venueResponse.setVenueName(venue.getVenueName());
            venueResponse.setVenueAddress(venue.getAddress());

            venueResponseList.add(venueResponse);
        }

        return venueResponseList;
    }
}
