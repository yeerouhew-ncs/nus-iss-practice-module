package tbs.tbsapi.factory;

import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;

@Component
public class SportsEvents implements Events {
    @Override
    public Event addEvent(AddEventDto addEventDto) {
        return Event.builder()
                .eventName(addEventDto.getEventName())
                .eventToDt(addEventDto.getEventToDt())
                .eventFromDt(addEventDto.getEventFromDt())
                .subjectId(addEventDto.getSubjectId())
                .planId(addEventDto.getPlanId())
                .artistName(addEventDto.getArtistName())
                .eventType(addEventDto.getEventType())
                .build();
    }
}
