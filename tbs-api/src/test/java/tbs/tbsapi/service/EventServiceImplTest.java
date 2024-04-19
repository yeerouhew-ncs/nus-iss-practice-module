package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tbs.tbsapi.domain.*;
import tbs.tbsapi.domain.enums.EventType;
import tbs.tbsapi.domain.enums.SeatStatus;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.factory.Events;
import tbs.tbsapi.factory.EventsContext;
import tbs.tbsapi.repository.*;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.EventDetailsResponse;
import tbs.tbsapi.vo.response.GetEventResponse;
import tbs.tbsapi.vo.response.GetSectionSeatResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SeatingPlanRepository seatingPlanRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SectionSeatRepository sectionSeatRepository;

    @Mock
    private SeatReservationRepository seatReservationRepository;

    @Mock
    private EventsContext eventsContext;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void addEvent(){
        AddEventDto addEventDto = new AddEventDto();
        addEventDto.setEventType(EventType.CONCERT);

        Events events = mock(Events.class);
        when(eventsContext.getEvent(addEventDto.getEventType())).thenReturn(events);

        Event preSaveEvent = new Event();
        when(events.addEvent(addEventDto)).thenReturn(preSaveEvent);

        Event savedEvent = new Event();
        savedEvent.setEventId(1);
        savedEvent.setEventName("Test Event");
        savedEvent.setEventFromDt(LocalDateTime.of(2024, 4, 1, 0, 0));
        savedEvent.setEventToDt(LocalDateTime.of(2024, 4, 1, 10, 0));
        savedEvent.setSubjectId(1);
        savedEvent.setPlanId(1);
        savedEvent.setArtistName("Test Artist");
        savedEvent.setEventType(EventType.CONCERT);
        savedEvent.setGenre("");
        when(eventRepository.save(any())).thenReturn(savedEvent);

        AddEventResponse response = eventService.addEvent(addEventDto);

        assertEquals("200", response.getStatusCode());
        assertEquals("SUCCESS", response.getMessage());
        assertEquals(1, response.getEventId());
        assertEquals("Test Event", response.getEventName());
        assertEquals(LocalDateTime.of(2024, 4, 1, 0, 0), response.getEventFromDt());
        assertEquals(LocalDateTime.of(2024, 4, 1, 10, 0), response.getEventToDt());
        assertEquals(1, response.getSubjectId());
        assertEquals(1, response.getPlanId());
        assertEquals("Test Artist", response.getArtistName());
        assertEquals(EventType.CONCERT, response.getEventType());
        assertEquals("", response.getGenre());


    }
//    @Test
//    void addConcert() {
//        AddEventDto addEventDto = new AddEventDto();
//        when(concertFactory.addEvent(addEventDto)).thenReturn(new Event());
//        when(eventRepository.save(any())).thenReturn(new Event());
//
//        AddEventResponse response = eventService.addConcert(addEventDto);
//
//        assertEquals("200", response.getStatusCode());
//        assertEquals("SUCCESS", response.getMessage());
//    }
//
//    @Test
//    void addSportsEvent() {
//        AddEventDto addEventDto = new AddEventDto();
//        when(sportsEventFactory.addEvent(addEventDto)).thenReturn(new Event());
//        when(eventRepository.save(any())).thenReturn(new Event());
//
//        AddEventResponse response = eventService.addSportsEvent(addEventDto);
//
//        assertEquals("200", response.getStatusCode());
//        assertEquals("SUCCESS", response.getMessage());
//    }
    @Test
    void testGetListOfEvents() {
        GetListOfEventRequest request = new GetListOfEventRequest();
        request.setEventId(1);
        request.setEventName("Test Event");
        request.setArtistName("Test Artist");
        request.setEventFromDt(LocalDateTime.of(2024, 4, 1, 0, 0));
        request.setEventToDt(LocalDateTime.of(2024, 4, 30, 0, 0));
        request.setSubjectId(1);

        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<GetEventResponse> mockPage = mock(Page.class);
        when(eventRepository.findEventList(1,"Test Event", "Test Artist", LocalDateTime.of(2024, 4, 1, 0, 0), LocalDateTime.of(2024, 4, 30, 0, 0), 1, pageable))
                .thenReturn(mockPage);

        Page<GetEventResponse> resultPage = eventService.getListOfEvents(pageable, request);

        verify(eventRepository).findEventList(
                1, "Test Event", "Test Artist",
                LocalDateTime.of(2024, 4, 1, 0, 0),
                LocalDateTime.of(2024, 4, 30, 0, 0),
                1, pageable);

        assertEquals(mockPage, resultPage);
    }
    @Test
    void getListOfEvents() {
        Pageable pageable = mock(Pageable.class);
        GetListOfEventRequest request = new GetListOfEventRequest();
        request.setEventId(1);
        request.setEventName("Test Event");
        request.setArtistName("Test Artist");
        LocalDateTime eventFromDt = LocalDateTime.of(2024, 4, 19, 0, 0);
        LocalDateTime eventToDt = LocalDateTime.of(2024, 4, 20, 0, 0);
        request.setEventFromDt(eventFromDt);
        request.setEventToDt(eventToDt);

        Page<GetEventResponse> mockedPage = mock(Page.class);

        when(eventRepository.findEventList(
                eq(1), eq("Test Event"), eq("Test Artist"),
                eq(eventFromDt), eq(eventToDt), eq(null), eq(pageable)))
                .thenReturn(mockedPage);

        Page<GetEventResponse> response = eventService.getListOfEvents(pageable, request);
        verify(eventRepository).findEventList(
                eq(1), eq("Test Event"), eq("Test Artist"),
                eq(eventFromDt), eq(eventToDt), eq(null), eq(pageable));
    }

    @Test
    void getEventDetails_SeatStatusReserved() {
        GetEventRequest request = new GetEventRequest();
        request.setEventId(1);

        Event event = new Event();
        event.setEventId(1);
        event.setSubjectId(1);
        event.setEventName("Test Event");
        event.setArtistName("Test Artist");

        SeatingPlan seatingPlan = new SeatingPlan();
        seatingPlan.setPlanId(1);
        seatingPlan.setVenueId(1);

        Venue venue = new Venue();
        venue.setVenueId(1);
        venue.setVenueName("Test Venue");
        venue.setAddress("Test Address");

        SectionSeat sectionSeat = new SectionSeat();
        sectionSeat.setSectionId(1);
        sectionSeat.setTotalSeats(100);
        sectionSeat.setNoSeatsLeft(50);
        sectionSeat.setSeatPrice(10.0);
        sectionSeat.setSeatSectionDescription("Test Section");
        sectionSeat.setSectionRow(10);
        sectionSeat.setSectionCol(10);

        List<SectionSeat> sectionSeats = Collections.singletonList(sectionSeat);
        when(sectionSeatRepository.findAllByPlanId(seatingPlan.getPlanId())).thenReturn(sectionSeats);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setSeatStatus(SeatStatus.available);

        List<Seat> seats = Collections.singletonList(seat);
        when(seatRepository.findAllBySectionId(sectionSeat.getSectionId())).thenReturn(seats);

        SeatReservation seatReservation = new SeatReservation();
        seatReservation.setSeatId(1);

        List<SeatReservation> reservedSeats = Collections.singletonList(seatReservation);
        when(seatReservationRepository.findSeatReservationByEventId(anyInt(), anyInt())).thenReturn(reservedSeats);

        when(eventRepository.findByEventId(request.getEventId())).thenReturn(event);
        when(seatingPlanRepository.findByPlanId(event.getPlanId())).thenReturn(seatingPlan);
        when(venueRepository.findByVenueId(seatingPlan.getVenueId())).thenReturn(venue);

        EventDetailsResponse response = eventService.getEventDetails(request);

        assertEquals(event.getEventId(), response.getEventId());
        assertEquals(event.getEventName(), response.getEventName());
        assertEquals(event.getArtistName(), response.getArtistName());
        assertEquals(seatingPlan.getPlanId(), response.getSeatingPlanResponse().getPlanId());
        assertEquals(venue.getVenueId(), response.getSeatingPlanResponse().getVenueId());
        assertEquals(venue.getVenueName(), response.getSeatingPlanResponse().getVenueName());
        assertEquals(venue.getAddress(), response.getSeatingPlanResponse().getAddress());

        List<GetSectionSeatResponse> sectionSeatResponses = response.getSeatingPlanResponse().getSectionSeatResponses();
        assertEquals(1, sectionSeatResponses.size());

        GetSectionSeatResponse sectionSeatResponse = sectionSeatResponses.get(0);
        assertEquals(sectionSeat.getSectionId(), sectionSeatResponse.getSectionId());
        assertEquals(sectionSeat.getTotalSeats(), sectionSeatResponse.getTotalSeats());
        assertEquals(sectionSeat.getNoSeatsLeft(), sectionSeatResponse.getNoSeatsLeft());
        assertEquals(sectionSeat.getSeatPrice(), sectionSeatResponse.getSeatPrice());
        assertEquals(sectionSeat.getSeatSectionDescription(), sectionSeatResponse.getSeatSectionDescription());
        assertEquals(sectionSeat.getSectionRow(), sectionSeatResponse.getSectionRow());
        assertEquals(sectionSeat.getSectionCol(), sectionSeatResponse.getSectionCol());

        assertEquals(SeatStatus.reserved, sectionSeatResponse.getSeatResponses().get(0).getSeatStatus());
    }

    @Test
    void getEventDetails_SeatStatusAvailable() {
        GetEventRequest request = new GetEventRequest();
        request.setEventId(1);

        Event event = new Event();
        event.setEventId(1);
        event.setSubjectId(1);
        event.setEventName("Test Event");
        event.setArtistName("Test Artist");

        SeatingPlan seatingPlan = new SeatingPlan();
        seatingPlan.setPlanId(1);
        seatingPlan.setVenueId(1);

        Venue venue = new Venue();
        venue.setVenueId(1);
        venue.setVenueName("Test Venue");
        venue.setAddress("Test Address");

        SectionSeat sectionSeat = new SectionSeat();
        sectionSeat.setSectionId(1);
        sectionSeat.setTotalSeats(100);
        sectionSeat.setNoSeatsLeft(50);
        sectionSeat.setSeatPrice(10.0);
        sectionSeat.setSeatSectionDescription("Test Section");
        sectionSeat.setSectionRow(10);
        sectionSeat.setSectionCol(10);

        List<SectionSeat> sectionSeats = Collections.singletonList(sectionSeat);
        when(sectionSeatRepository.findAllByPlanId(seatingPlan.getPlanId())).thenReturn(sectionSeats);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setSeatStatus(SeatStatus.reserved);

        List<Seat> seats = Collections.singletonList(seat);
        when(seatRepository.findAllBySectionId(sectionSeat.getSectionId())).thenReturn(seats);

        SeatReservation seatReservation = new SeatReservation();


        List<SeatReservation> reservedSeats = Collections.singletonList(seatReservation);
        when(seatReservationRepository.findSeatReservationByEventId(anyInt(), anyInt())).thenReturn(reservedSeats);

        when(eventRepository.findByEventId(request.getEventId())).thenReturn(event);
        when(seatingPlanRepository.findByPlanId(event.getPlanId())).thenReturn(seatingPlan);
        when(venueRepository.findByVenueId(seatingPlan.getVenueId())).thenReturn(venue);

        EventDetailsResponse response = eventService.getEventDetails(request);

        assertEquals(SeatStatus.available, response.getSeatingPlanResponse().getSectionSeatResponses().get(0).getSeatResponses().get(0).getSeatStatus());
    }

    @Test
    void editEvent_DontExist() {
        EditEventDto editEventDto = new EditEventDto();
        editEventDto.setEventId(1);
        editEventDto.setEventName("Updated Event Name");

        when(eventRepository.findByEventId(editEventDto.getEventId())).thenReturn(null);

        List<String> response = eventService.editEvent(editEventDto);

        assertEquals(List.of("200", "Event does not exist"), response);
        verify(eventRepository, times(1)).findByEventId(editEventDto.getEventId());
        verify(eventRepository, never()).updateUser(any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void editEvent_Success() {
        EditEventDto editEventDto = new EditEventDto();
        editEventDto.setEventId(1);
        editEventDto.setEventName("Updated Event Name");
        editEventDto.setArtistName("Updated Artist Name");
        editEventDto.setEventFromDt(LocalDateTime.parse("2024-04-15T10:00:00"));
        editEventDto.setEventToDt(LocalDateTime.parse("2024-04-15T12:00:00"));
        editEventDto.setPlanId(2);
        editEventDto.setSubjectId(3);
        editEventDto.setEventType(EventType.CONCERT);
        editEventDto.setGenre("Updated Genre");

        when(eventRepository.findByEventId(editEventDto.getEventId())).thenReturn(mock(Event.class));
        when(eventRepository.updateUser(editEventDto.getEventId(),
                editEventDto.getEventName(),
                editEventDto.getArtistName(),
                editEventDto.getEventFromDt(),
                editEventDto.getEventToDt(),
                editEventDto.getPlanId(),
                editEventDto.getSubjectId(),
                editEventDto.getEventType(),
                editEventDto.getGenre())).thenReturn(1);

        List<String> response = eventService.editEvent(editEventDto);

        assertEquals(Arrays.asList("200", "Event updated successfully"), response);
        verify(eventRepository, times(1)).findByEventId(editEventDto.getEventId());
        verify(eventRepository, times(1)).updateUser(editEventDto.getEventId(),
                editEventDto.getEventName(),
                editEventDto.getArtistName(),
                editEventDto.getEventFromDt(),
                editEventDto.getEventToDt(),
                editEventDto.getPlanId(),
                editEventDto.getSubjectId(),
                editEventDto.getEventType(),
                editEventDto.getGenre());
    }
    @Test
    void editEvent_NotUpdated() {
        EditEventDto editEventDto = new EditEventDto();
        editEventDto.setEventId(1);
        editEventDto.setEventName("Updated Event Name");
        editEventDto.setArtistName("Updated Artist Name");
        editEventDto.setEventFromDt(LocalDateTime.now());
        editEventDto.setEventToDt(LocalDateTime.now());
        editEventDto.setPlanId(2);
        editEventDto.setSubjectId(3);
        editEventDto.setEventType(EventType.CONCERT);
        editEventDto.setGenre("Updated Genre");

        when(eventRepository.findByEventId(editEventDto.getEventId())).thenReturn(new Event());
        when(eventRepository.updateUser(eq(1), eq("Updated Event Name"), anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyInt(), anyInt(), eq(EventType.CONCERT), anyString())).thenReturn(0);

        List<String> response = eventService.editEvent(editEventDto);

        assertEquals(List.of("400", "Event not updated"), response);
    }


}
