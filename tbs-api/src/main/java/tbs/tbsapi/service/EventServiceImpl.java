package tbs.tbsapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.factory.ConcertFactory;
import tbs.tbsapi.factory.SportsEventFactory;
import tbs.tbsapi.repository.EventRepository;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ConcertFactory concertFactory;

    @Autowired
    private SportsEventFactory sportsEventFactory;

    public AddEventResponse addConcert(AddEventDto addEventDto) {
        Event preSaveEvent = concertFactory.addEvent(addEventDto);
        log.info("CONCERT EVENT {} ", preSaveEvent);

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
        eventResponse.setEventType(saveEvent.getEventType());
        eventResponse.setGenre(saveEvent.getGenre());

        return eventResponse;
    }

    public AddEventResponse addSportsEvent(AddEventDto addEventDto) {
        Event preSaveEvent = sportsEventFactory.addEvent(addEventDto);
        log.info("SPORTS EVENT {} ", preSaveEvent);

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
        eventResponse.setEventType(saveEvent.getEventType());

        return eventResponse;
    }

    // TODO: remove
    public AddEventResponse addEvent(AddEventDto addEventDto) {
        Event preSaveEvent = Event.builder()
                .eventName(addEventDto.getEventName())
                .eventToDt(addEventDto.getEventToDt())
                .eventFromDt(addEventDto.getEventFromDt())
                .subjectId(addEventDto.getSubjectId())
                .planId(addEventDto.getPlanId())
                .artistName(addEventDto.getArtistName())
                .eventType(addEventDto.getEventType())
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
        eventResponse.setEventType(saveEvent.getEventType());

        return eventResponse;
    }

    public Page<GetEventResponse> getListOfEvents(Pageable pageable, GetListOfEventRequest getListOfEventRequest) {
        if(Objects.equals(getListOfEventRequest.getEventName(), "")) getListOfEventRequest.setEventName(null);
        if(Objects.equals(getListOfEventRequest.getArtistName(), "")) getListOfEventRequest.setArtistName(null);

        return eventRepository.findEventList(
                getListOfEventRequest.getEventId(),
                getListOfEventRequest.getEventName(),
                getListOfEventRequest.getArtistName(),
                getListOfEventRequest.getEventFromDt(),
                getListOfEventRequest.getEventToDt(),
                getListOfEventRequest.getSubjectId(),
                pageable
        );
    }

    public GetEventResponse getEventDetails(GetEventRequest getEventRequest) {
        Event event = eventRepository.findByEventId(getEventRequest.getEventId());

        GetEventResponse eventResponse = new GetEventResponse();
        eventResponse.setEventName(event.getEventName());
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventFromDt(event.getEventFromDt());
        eventResponse.setArtistName(event.getArtistName());
        eventResponse.setPlanId(event.getPlanId());
        eventResponse.setEventToDt(event.getEventToDt());
        eventResponse.setSubjectId(event.getSubjectId());
        eventResponse.setEventType(event.getEventType());
        eventResponse.setGenre(event.getGenre());

        // retrieve seat reservation (id, order_id, seat_id, section_id)


        // retrieve plan section seats (id, no_seats_left, plan_id, seat_price, section_desc, section_col,
        // section_row, total_seats)
        // retrieve seat table (id, seat_col, seat_name, seat_row, seat_status, section_id)
        // loop through seat reservation and compare the seat and seat reservation
        // if it matches, set status to reserved

        return eventResponse;
    }

    @Transactional
    public List<String> editEvent(EditEventDto editEventDto) {
        List<String> response = new ArrayList<>();

        if(eventRepository.findByEventId(editEventDto.getEventId()) == null) {
            response.add("200");
            response.add("Event does not exist");
            return response;
        }

        Integer updatedEvent = eventRepository.updateUser(editEventDto.getEventId(),
                editEventDto.getEventName(),
                editEventDto.getArtistName(),
                editEventDto.getEventFromDt(),
                editEventDto.getEventToDt(),
                editEventDto.getPlanId(),
                editEventDto.getSubjectId(),
                editEventDto.getEventType(),
                editEventDto.getGenre());

        if(updatedEvent == 1) {
            response.add("200");
            response.add("Event updated successfully");
            return response;
        }

        response.add("400");
        response.add("Event not updated");
        return response;
    }
}
