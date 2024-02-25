package tbs.tbsapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.GetListOfEventResponse;

public interface EventService {
    public AddEventResponse addEvent(AddEventDto addEventDto);
    public Page<GetListOfEventResponse> getListOfEvents(Pageable pageable, GetListOfEventRequest getListOfEventRequest);
}
