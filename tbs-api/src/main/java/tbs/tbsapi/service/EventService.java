package tbs.tbsapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.EventDetailsResponse;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.util.List;

public interface EventService {
    public AddEventResponse addEvent(AddEventDto addEventDto);
//    public AddEventResponse addSportsEvent(AddEventDto addEventDto);
//    public AddEventResponse addEvent(AddEventDto addEventDto);
    public Page<GetEventResponse> getListOfEvents(Pageable pageable, GetListOfEventRequest getListOfEventRequest);
    public EventDetailsResponse getEventDetails(GetEventRequest getEventRequest);
    public List<String> editEvent(EditEventDto editEventDto);
}
