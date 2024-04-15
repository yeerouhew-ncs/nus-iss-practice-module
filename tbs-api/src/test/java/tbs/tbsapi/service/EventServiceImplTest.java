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
import tbs.tbsapi.factory.ConcertFactory;
import tbs.tbsapi.factory.SportsEventFactory;
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
    private ConcertFactory concertFactory;

    @Mock
    private SportsEventFactory sportsEventFactory;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void addConcert() {
        AddEventDto addEventDto = new AddEventDto();
        when(concertFactory.addEvent(addEventDto)).thenReturn(new Event());
        when(eventRepository.save(any())).thenReturn(new Event());

        AddEventResponse response = eventService.addConcert(addEventDto);

        assertEquals("200", response.getStatusCode());
        assertEquals("SUCCESS", response.getMessage());
    }

    @Test
    void addSportsEvent() {
        AddEventDto addEventDto = new AddEventDto();
        when(sportsEventFactory.addEvent(addEventDto)).thenReturn(new Event());
        when(eventRepository.save(any())).thenReturn(new Event());

        AddEventResponse response = eventService.addSportsEvent(addEventDto);

        assertEquals("200", response.getStatusCode());
        assertEquals("SUCCESS", response.getMessage());
    }
    @Test
    void testGetListOfEvents() {
        // Mock data for the request
        GetListOfEventRequest request = new GetListOfEventRequest();
        request.setEventId(1);
        request.setEventName("Test Event");
        request.setArtistName("Test Artist");
        request.setEventFromDt(LocalDateTime.of(2024, 4, 1, 0, 0));
        request.setEventToDt(LocalDateTime.of(2024, 4, 30, 0, 0));
        request.setSubjectId(1);

        // Mock data for the pageable
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Mock the repository response
        Page<GetEventResponse> mockPage = mock(Page.class);
        when(eventRepository.findEventList(1,"Test Event", "Test Artist", LocalDateTime.of(2024, 4, 1, 0, 0), LocalDateTime.of(2024, 4, 30, 0, 0), 1, pageable))
                .thenReturn(mockPage);

        // Call the method under test
        Page<GetEventResponse> resultPage = eventService.getListOfEvents(pageable, request);

        // Verify that the repository method is called with the correct arguments
        verify(eventRepository).findEventList(
                1, "Test Event", "Test Artist",
                LocalDateTime.of(2024, 4, 1, 0, 0),
                LocalDateTime.of(2024, 4, 30, 0, 0),
                1, pageable);

        // Assert the result
        assertEquals(mockPage, resultPage);
    }
    @Test
    void getListOfEvents() {
        Pageable pageable = mock(Pageable.class);
        GetListOfEventRequest request = new GetListOfEventRequest();
        request.setEventId(1);
        request.setEventId(1);
        request.setEventToDt(LocalDateTime.now());
        request.setEventFromDt(LocalDateTime.now());
        when(eventRepository.findEventList(eq(1), eq(null), eq(null), any(LocalDateTime.class), any(LocalDateTime.class), eq(null), eq(pageable)))
                .thenReturn(mock(Page.class));

        Page<GetEventResponse> response = eventService.getListOfEvents(pageable, request);
    }

    @Test
    void getEventDetails() {
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

        // Mock sectionSeatRepository behavior
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

        // Mock seatRepository behavior
        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setSeatStatus(SeatStatus.available);

        List<Seat> seats = Collections.singletonList(seat);
        when(seatRepository.findAllBySectionId(sectionSeat.getSectionId())).thenReturn(seats);

        // Mock seatReservationRepository behavior
        SeatReservation seatReservation = new SeatReservation();
        seatReservation.setSeatId(1);

        List<SeatReservation> reservedSeats = Collections.singletonList(seatReservation);
        when(seatReservationRepository.findSeatReservationByEventId(anyInt(), anyInt())).thenReturn(reservedSeats);

        when(eventRepository.findByEventId(request.getEventId())).thenReturn(event);
        when(seatingPlanRepository.findByPlanId(event.getPlanId())).thenReturn(seatingPlan);
        when(venueRepository.findByVenueId(seatingPlan.getVenueId())).thenReturn(venue);

        // Call the method under test
        EventDetailsResponse response = eventService.getEventDetails(request);

        // Assertions
        assertEquals(event.getEventId(), response.getEventId());
        assertEquals(event.getEventName(), response.getEventName());
        assertEquals(event.getArtistName(), response.getArtistName());
        assertEquals(seatingPlan.getPlanId(), response.getSeatingPlanResponse().getPlanId());
        assertEquals(venue.getVenueId(), response.getSeatingPlanResponse().getVenueId());
        assertEquals(venue.getVenueName(), response.getSeatingPlanResponse().getVenueName());
        assertEquals(venue.getAddress(), response.getSeatingPlanResponse().getAddress());

        // Additional assertions for section seat responses
        List<GetSectionSeatResponse> sectionSeatResponses = response.getSeatingPlanResponse().getSectionSeatResponses();
        assertEquals(1, sectionSeatResponses.size()); // Assuming one section seat response in this case

        GetSectionSeatResponse sectionSeatResponse = sectionSeatResponses.get(0);
        assertEquals(sectionSeat.getSectionId(), sectionSeatResponse.getSectionId());
        assertEquals(sectionSeat.getTotalSeats(), sectionSeatResponse.getTotalSeats());
        assertEquals(sectionSeat.getNoSeatsLeft(), sectionSeatResponse.getNoSeatsLeft());
        assertEquals(sectionSeat.getSeatPrice(), sectionSeatResponse.getSeatPrice());
        assertEquals(sectionSeat.getSeatSectionDescription(), sectionSeatResponse.getSeatSectionDescription());
        assertEquals(sectionSeat.getSectionRow(), sectionSeatResponse.getSectionRow());
        assertEquals(sectionSeat.getSectionCol(), sectionSeatResponse.getSectionCol());
        // Assuming seat status is correctly set based on reservation
        assertEquals(SeatStatus.reserved, sectionSeatResponse.getSeatResponses().get(0).getSeatStatus());
    }

    @Test
    void editEvent_DontExist() {
        // Arrange
        EditEventDto editEventDto = new EditEventDto();
        editEventDto.setEventId(1);
        editEventDto.setEventName("Updated Event Name");

        when(eventRepository.findByEventId(editEventDto.getEventId())).thenReturn(null);

        // Act
        List<String> response = eventService.editEvent(editEventDto);

        // Assert
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
        // Arrange
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

        // Act
        List<String> response = eventService.editEvent(editEventDto);

        // Assert
        assertEquals(List.of("400", "Event not updated"), response);
    }


}
