package tbs.tbsapi.factory;

import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;

public interface EventFactory {
    Event addEvent(AddEventDto addEventDto);
}
