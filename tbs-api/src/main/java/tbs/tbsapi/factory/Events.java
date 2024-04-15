package tbs.tbsapi.factory;

import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;

public interface Events {
    Event addEvent(AddEventDto addEventDto);
}
