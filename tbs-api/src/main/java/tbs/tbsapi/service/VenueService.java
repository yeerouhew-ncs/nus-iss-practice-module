package tbs.tbsapi.service;

import tbs.tbsapi.vo.request.GetVenueRequest;
import tbs.tbsapi.vo.response.GetVenueResponse;

import java.util.List;

public interface VenueService {
    GetVenueResponse getVenueDetails(GetVenueRequest venueRequest);

    List<GetVenueResponse> getVenueList();
}
