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
        // TODO: change GetEventResponse return type
        Event event = eventRepository.findByEventId(getEventRequest.getEventId());

        GetEventResponse eventResponse = new GetEventResponse();
        eventResponse.setEventName(event.getEventName());
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventFromDt(event.getEventFromDt());
        eventResponse.setArtistName(event.getArtistName());
        eventResponse.setPlanId(event.getPlanId());
        eventResponse.setEventToDt(event.getEventToDt());
        eventResponse.setSubjectId(event.getSubjectId());


        // TODO: get plan details
        // TODO: get subject details

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
                editEventDto.getSubjectId());

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
