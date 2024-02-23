package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.repository.EventRepository;
import tbs.tbsapi.vo.response.AddEventResponse;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    public AddEventResponse addEvent(AddEventDto addEventDto) {
        Event preSaveEvent = Event.builder()
                .eventName(addEventDto.getEventName())
                .eventToDt(addEventDto.getEventToDt())
                .eventFromDt(addEventDto.getEventFromDt())
                .subjectId(addEventDto.getSubjectId())
                .planId(addEventDto.getPlanId())
                .artistName(addEventDto.getArtistName())
                .build();

        Event saveEvent = eventRepository.save(preSaveEvent);

        AddEventResponse eventResponse = new AddEventResponse();
        eventResponse.setStatusCode("200");
        eventResponse.setMessage("SUCCESS");
        eventResponse.setEventId(saveEvent.getEventId());
        eventResponse.setEventName(saveEvent.getEventName());
        eventResponse.setEventFromDt(saveEvent.getEventFromDt());
        eventResponse.setEventToDt(saveEvent.getEventToDt());
        eventResponse.setSubjectId(saveEvent.getSubjectId());
        eventResponse.setPlanId(saveEvent.getPlanId());
        eventResponse.setArtistName(saveEvent.getArtistName());

        return eventResponse;
    }
}
