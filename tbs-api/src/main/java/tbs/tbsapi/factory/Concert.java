package tbs.tbsapi.factory;

import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;

import java.util.Objects;

@Component
public class Concert implements Events {
    @Override
    public Event addEvent(AddEventDto addEventDto) {
        String genre;
        if(Objects.equals(addEventDto.getGenre(), "")) {
            genre = null;
        } else {
            genre = addEventDto.getGenre();
        }
        return Event.builder()
                .eventName(addEventDto.getEventName())
                .eventToDt(addEventDto.getEventToDt())
                .eventFromDt(addEventDto.getEventFromDt())
                .subjectId(addEventDto.getSubjectId())
                .planId(addEventDto.getPlanId())
                .artistName(addEventDto.getArtistName())
                .eventType(addEventDto.getEventType())
                .genre(genre)
                .build();
    }
}
