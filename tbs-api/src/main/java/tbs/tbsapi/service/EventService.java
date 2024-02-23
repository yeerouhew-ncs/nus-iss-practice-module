package tbs.tbsapi.service;

import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.vo.response.AddEventResponse;

public interface EventService {
    public AddEventResponse addEvent(AddEventDto addEventDto);
}
