package tbs.tbsapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.*;
import tbs.tbsapi.domain.enums.SeatStatus;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.factory.Events;
import tbs.tbsapi.factory.EventsContext;
import tbs.tbsapi.repository.*;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SectionSeatRepository sectionSeatRepository;

    @Autowired
    private SeatingPlanRepository seatingPlanRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

//    @Autowired
//    private Concert concert;
//
//    @Autowired
//    private SportsEvents sportsEvent;

    @Autowired
    private EventsContext eventsContext;

    public AddEventResponse addEvent(AddEventDto addEventDto) {
        Events events = eventsContext.getEvent(addEventDto.getEventType());
        Event preSaveEvent = events.addEvent(addEventDto);
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

//    public AddEventResponse addSportsEvent(AddEventDto addEventDto) {
//        Event preSaveEvent = sportsEvent.addEvent(addEventDto);
//        log.info("SPORTS EVENT {} ", preSaveEvent);
//
//        Event saveEvent = eventRepository.save(preSaveEvent);
//
//        AddEventResponse eventResponse = new AddEventResponse();
//        eventResponse.setStatusCode("200");
//        eventResponse.setMessage("SUCCESS");
//        eventResponse.setEventId(saveEvent.getEventId());
//        eventResponse.setEventName(saveEvent.getEventName());
//        eventResponse.setEventFromDt(saveEvent.getEventFromDt());
//        eventResponse.setEventToDt(saveEvent.getEventToDt());
//        eventResponse.setSubjectId(saveEvent.getSubjectId());
//        eventResponse.setPlanId(saveEvent.getPlanId());
//        eventResponse.setArtistName(saveEvent.getArtistName());
//        eventResponse.setEventType(saveEvent.getEventType());
//
//        return eventResponse;
//    }

    // TODO: remove
//    public AddEventResponse addEvent(AddEventDto addEventDto) {
//        Event preSaveEvent = Event.builder()
//                .eventName(addEventDto.getEventName())
//                .eventToDt(addEventDto.getEventToDt())
//                .eventFromDt(addEventDto.getEventFromDt())
//                .subjectId(addEventDto.getSubjectId())
//                .planId(addEventDto.getPlanId())
//                .artistName(addEventDto.getArtistName())
//                .eventType(addEventDto.getEventType())
//                .build();
//
//        Event saveEvent = eventRepository.save(preSaveEvent);
//
//        AddEventResponse eventResponse = new AddEventResponse();
//        eventResponse.setStatusCode("200");
//        eventResponse.setMessage("SUCCESS");
//        eventResponse.setEventId(saveEvent.getEventId());
//        eventResponse.setEventName(saveEvent.getEventName());
//        eventResponse.setEventFromDt(saveEvent.getEventFromDt());
//        eventResponse.setEventToDt(saveEvent.getEventToDt());
//        eventResponse.setSubjectId(saveEvent.getSubjectId());
//        eventResponse.setPlanId(saveEvent.getPlanId());
//        eventResponse.setArtistName(saveEvent.getArtistName());
//        eventResponse.setEventType(saveEvent.getEventType());
//
//        return eventResponse;
//    }

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

    public EventDetailsResponse getEventDetails(GetEventRequest getEventRequest) {
        Event event = eventRepository.findByEventId(getEventRequest.getEventId());

        EventDetailsResponse eventResponse = new EventDetailsResponse();
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
        SeatingPlan plan = seatingPlanRepository.findByPlanId(eventResponse.getPlanId());
        Venue venue = venueRepository.findByVenueId(plan.getVenueId());

        // retrieve plan section seats (id, no_seats_left, plan_id, seat_price, section_desc, section_col,
        // section_row, total_seats)
        GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
        seatingPlanResponse.setPlanId(plan.getPlanId());
        seatingPlanResponse.setPlanName(plan.getPlanName());
        seatingPlanResponse.setPlanRow(plan.getPlanRow());
        seatingPlanResponse.setPlanCol(plan.getPlanCol());
        seatingPlanResponse.setVenueId(plan.getVenueId());
        seatingPlanResponse.setVenueName(venue.getVenueName());
        seatingPlanResponse.setAddress(venue.getAddress());

        List<SectionSeat> sectionSeats = sectionSeatRepository.findAllByPlanId(plan.getPlanId());
        List<GetSectionSeatResponse> sectionSeatResponses = new ArrayList<>();
        // retrieve seat table (id, seat_col, seat_name, seat_row, seat_status, section_id)
        // loop through seat reservation and compare the seat and seat reservation
        // if it matches, set status to reserved
        for(SectionSeat sectionSeat: sectionSeats) {
            GetSectionSeatResponse sectionSeatResponse = new GetSectionSeatResponse();
            sectionSeatResponse.setSectionId(sectionSeat.getSectionId());
            sectionSeatResponse.setTotalSeats(sectionSeat.getTotalSeats());
            sectionSeatResponse.setNoSeatsLeft(sectionSeat.getNoSeatsLeft());
            sectionSeatResponse.setSeatPrice(sectionSeat.getSeatPrice());
            sectionSeatResponse.setSeatSectionDescription(sectionSeat.getSeatSectionDescription());
            sectionSeatResponse.setSectionRow(sectionSeat.getSectionRow());
            sectionSeatResponse.setSectionCol(sectionSeat.getSectionCol());

            List<SeatReservation> reservedSeats = seatReservationRepository.findSeatReservationByEventId(getEventRequest.getEventId(), sectionSeat.getSectionId());
            List<Integer> reservedSeatIds = reservedSeats.stream().map(SeatReservation::getSeatId).collect(Collectors.toList());
            log.info("SEAT RESERVATIONS: {} ", reservedSeats);

            List<Seat> seats = seatRepository.findAllBySectionId(sectionSeat.getSectionId());
            List<Seat> updatedSeats = new ArrayList<>();
            for(Seat seat: seats) {
                if(reservedSeatIds.contains(seat.getSeatId())) {
                    seat.setSeatStatus(SeatStatus.reserved);
                } else {
                    seat.setSeatStatus(SeatStatus.available);
                }
                updatedSeats.add(seat);
            }

            sectionSeatResponse.setSeatResponses(updatedSeats);
            sectionSeatResponses.add(sectionSeatResponse);
        }

        seatingPlanResponse.setSectionSeatResponses(sectionSeatResponses);
        eventResponse.setSeatingPlanResponse(seatingPlanResponse);
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
