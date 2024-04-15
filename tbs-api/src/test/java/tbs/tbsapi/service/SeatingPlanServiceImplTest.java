package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbs.tbsapi.domain.*;
import tbs.tbsapi.domain.enums.SeatStatus;
import tbs.tbsapi.dto.*;
import tbs.tbsapi.repository.*;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatingPlanServiceImplTest {

    @Mock
    private SeatingPlanRepository seatingPlanRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private SectionSeatRepository sectionSeatRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatingPlanServiceImpl seatingPlanService;

    @Test
    void Test_AddSeatingPlan() {
        AddSeatingPlanDto dto = new AddSeatingPlanDto();
        dto.setVenueId(1);
        dto.setPlanName("Test Plan");
        dto.setPlanRow(10);
        dto.setPlanCol(15);

        when(seatingPlanRepository.save(any(SeatingPlan.class))).thenReturn(new SeatingPlan());

        SectionSeatDto sectionSeatDto = new SectionSeatDto();
        sectionSeatDto.setTotalSeats(100);
        sectionSeatDto.setSeatPrice(50.0);
        sectionSeatDto.setSectionDesc("VIP");
        sectionSeatDto.setSectionRow(5);
        sectionSeatDto.setSectionCol(10);
        List<SectionSeatDto> sectionSeats = new ArrayList<>();
        sectionSeats.add(sectionSeatDto);
        dto.setSectionSeats(sectionSeats);
        SeatDto seatDto = new SeatDto();
        seatDto.setSeatCol(1);
        seatDto.setSeatRow(1);
        List<SeatDto> seatDtos = new ArrayList<>();
        seatDtos.add(seatDto);
        sectionSeatDto.setSeats(seatDtos);

        SectionSeat sectionSeat = new SectionSeat();
        when(sectionSeatRepository.save(any(SectionSeat.class))).thenReturn(sectionSeat);

        Seat saveSeat = new Seat();
        saveSeat.setSeatId(1);
        when(seatRepository.save(any(Seat.class))).thenReturn(saveSeat);

        AddSeatingPlanResponse response = seatingPlanService.addSeatingPlan(dto);

        assertEquals("200", response.getStatusCode());
        assertEquals("SUCCESS", response.getMessage());
        verify(seatingPlanRepository, times(1)).save(any(SeatingPlan.class));
    }

    @Test
    void testEditPlanDoesNotExist() {
        EditSeatingPlanDto dto = new EditSeatingPlanDto();
        dto.setPlanId(1);
        dto.setVenueId(1);
        dto.setPlanName("Plan");
        dto.setPlanRow(1);
        dto.setPlanCol(1);
        dto.setSectionSeats(new ArrayList<>());

        when(seatingPlanRepository.findByPlanId(anyInt())).thenReturn(null);

        List<String> response = seatingPlanService.editSeatingPlan(dto);

        assertEquals(2, response.size());
        assertEquals("200", response.get(0));
        assertEquals("PLAN DOES NOT EXIST", response.get(1));

        verify(seatingPlanRepository, times(1)).findByPlanId(anyInt());
        verifyNoMoreInteractions(seatingPlanRepository);
    }

    @Test
    void testEditSuccessfulPlanUpdate() {
        EditSeatingPlanDto dto = new EditSeatingPlanDto();
        dto.setPlanId(1);
        dto.setVenueId(1);
        dto.setPlanName("Plan");
        dto.setPlanRow(1);
        dto.setPlanCol(1);
        dto.setSectionSeats(new ArrayList<>());

        when(seatingPlanRepository.findByPlanId(anyInt())).thenReturn(new SeatingPlan());
        when(seatingPlanRepository.updatePlan(1, "Plan", 1, 1, 1)).thenReturn(1);

        List<String> response = seatingPlanService.editSeatingPlan(dto);

        assertEquals(2, response.size());
        assertEquals("200", response.get(0));
        assertEquals("SUCCESS", response.get(1));

        verify(seatingPlanRepository, times(1)).findByPlanId(anyInt());
        verify(seatingPlanRepository, times(1)).updatePlan(anyInt(), anyString(), anyInt(), anyInt(), anyInt());
        verifyNoMoreInteractions(seatingPlanRepository);
    }

    @Test
    void testEditPlanUpdateFailure() {
        EditSeatingPlanDto dto = new EditSeatingPlanDto();
        dto.setPlanId(1);
        dto.setVenueId(1);
        dto.setPlanName("Plan");
        dto.setPlanRow(1);
        dto.setPlanCol(1);
        dto.setSectionSeats(new ArrayList<>());

        when(seatingPlanRepository.findByPlanId(anyInt())).thenReturn(new SeatingPlan());
        when(seatingPlanRepository.updatePlan(1, "Plan", 1, 1, 1)).thenReturn(0);

        List<String> response = seatingPlanService.editSeatingPlan(dto);

        assertEquals(2, response.size());
        assertEquals("400", response.get(0));
        assertEquals("Plan not updated", response.get(1));

        verify(seatingPlanRepository, times(1)).findByPlanId(anyInt());
        verify(seatingPlanRepository, times(1)).updatePlan(anyInt(), anyString(), anyInt(), anyInt(), anyInt());
        verifyNoMoreInteractions(seatingPlanRepository);
    }

    @Test
    void testEditPlanCategoryPlanDoesNotExist() {
        EditPlanSectionSeatDto dto = new EditPlanSectionSeatDto();
        dto.setPlanId(1);
        dto.setSectionSeats(new ArrayList<>());

        when(seatingPlanRepository.findByPlanId(anyInt())).thenReturn(null);

        List<String> response = seatingPlanService.editPlanCategory(dto);

        assertEquals(2, response.size());
        assertEquals("200", response.get(0));
        assertEquals("PLAN DOES NOT EXIST", response.get(1));

        verify(seatingPlanRepository, times(1)).findByPlanId(anyInt());
        verifyNoMoreInteractions(seatingPlanRepository);
    }

    @Test
    void testGetListofSeatingPlans() {
        SeatingPlan seatingPlan1 = new SeatingPlan();
        seatingPlan1.setPlanId(1);
        seatingPlan1.setPlanName("Test Plan 1");
        seatingPlan1.setPlanRow(10);
        seatingPlan1.setPlanCol(15);
        seatingPlan1.setVenueId(1);

        SeatingPlan seatingPlan2 = new SeatingPlan();
        seatingPlan2.setPlanId(2);
        seatingPlan2.setPlanName("Test Plan 2");
        seatingPlan2.setPlanRow(8);
        seatingPlan2.setPlanCol(12);
        seatingPlan2.setVenueId(2);

        Venue venue1 = new Venue();
        venue1.setVenueId(1);
        venue1.setVenueName("Test Venue 1");
        venue1.setAddress("Test Address 1");

        Venue venue2 = new Venue();
        venue2.setVenueId(2);
        venue2.setVenueName("Test Venue 2");
        venue2.setAddress("Test Address 2");

        SectionSeat sectionSeat1 = new SectionSeat();
        sectionSeat1.setSectionId(1);
        sectionSeat1.setSeatPrice(50.0);
        sectionSeat1.setTotalSeats(100);
        sectionSeat1.setSeatSectionDescription("VIP");
        sectionSeat1.setSectionRow(5);
        sectionSeat1.setSectionCol(10);
        sectionSeat1.setNoSeatsLeft(50);

        SectionSeat sectionSeat2 = new SectionSeat();
        sectionSeat2.setSectionId(2);
        sectionSeat2.setSeatPrice(40.0);
        sectionSeat2.setTotalSeats(80);
        sectionSeat2.setSeatSectionDescription("Standard");
        sectionSeat2.setSectionRow(4);
        sectionSeat2.setSectionCol(8);
        sectionSeat2.setNoSeatsLeft(20);

        Seat seat1 = new Seat();
        seat1.setSeatId(1);
        seat1.setSeatName("A1");
        seat1.setSeatRow(1);
        seat1.setSeatCol(1);
        seat1.setSeatStatus(SeatStatus.available);

        Seat seat2 = new Seat();
        seat2.setSeatId(2);
        seat2.setSeatName("B2");
        seat2.setSeatRow(2);
        seat2.setSeatCol(2);
        seat2.setSeatStatus(SeatStatus.reserved);

        when(seatingPlanRepository.findAll()).thenReturn(List.of(seatingPlan1, seatingPlan2));
        when(venueRepository.findByVenueId(1)).thenReturn(venue1);
        when(venueRepository.findByVenueId(2)).thenReturn(venue2);
        when(sectionSeatRepository.findAllByPlanId(1)).thenReturn(List.of(sectionSeat1));
        when(sectionSeatRepository.findAllByPlanId(2)).thenReturn(List.of(sectionSeat2));
        when(seatRepository.findAllBySectionId(1)).thenReturn(List.of(seat1));
        when(seatRepository.findAllBySectionId(2)).thenReturn(List.of(seat2));

        List<GetSeatingPlanResponse> responses = seatingPlanService.getListofSeatingPlans();

        assertEquals(2, responses.size());

        //Seating Plan 1
        GetSeatingPlanResponse response1 = responses.get(0);
        assertEquals(1, response1.getPlanId());
        assertEquals("Test Plan 1", response1.getPlanName());
        assertEquals(10, response1.getPlanRow());
        assertEquals(15, response1.getPlanCol());
        assertEquals(1, response1.getVenueId());
        assertEquals("Test Venue 1", response1.getVenueName());
        assertEquals("Test Address 1", response1.getAddress());
        assertEquals(1, response1.getSectionSeatResponses().size());
        GetSectionSeatResponse sectionSeatResponse1 = response1.getSectionSeatResponses().get(0);
        assertEquals(50.0, sectionSeatResponse1.getSeatPrice());
        assertEquals(100, sectionSeatResponse1.getTotalSeats());
        assertEquals("VIP", sectionSeatResponse1.getSeatSectionDescription());
        assertEquals(5, sectionSeatResponse1.getSectionRow());
        assertEquals(10, sectionSeatResponse1.getSectionCol());
        assertEquals(50, sectionSeatResponse1.getNoSeatsLeft());
        assertEquals(1, sectionSeatResponse1.getSeatResponses().size());
        assertEquals(1, sectionSeatResponse1.getSeatResponses().get(0).getSeatId());

        //Seating Plan 2
        GetSeatingPlanResponse response2 = responses.get(1);
        assertEquals(2, response2.getPlanId());
        assertEquals("Test Plan 2", response2.getPlanName());
        assertEquals(8, response2.getPlanRow());
        assertEquals(12, response2.getPlanCol());
        assertEquals(2, response2.getVenueId());
        assertEquals("Test Venue 2", response2.getVenueName());
        assertEquals("Test Address 2", response2.getAddress());
        assertEquals(1, response2.getSectionSeatResponses().size());
        GetSectionSeatResponse sectionSeatResponse2 = response2.getSectionSeatResponses().get(0);
        assertEquals(40.0, sectionSeatResponse2.getSeatPrice());
        assertEquals(80, sectionSeatResponse2.getTotalSeats());
        assertEquals("Standard", sectionSeatResponse2.getSeatSectionDescription());
        assertEquals(4, sectionSeatResponse2.getSectionRow());
        assertEquals(8, sectionSeatResponse2.getSectionCol());
        assertEquals(20, sectionSeatResponse2.getNoSeatsLeft());
        assertEquals(1, sectionSeatResponse2.getSeatResponses().size());
        assertEquals(2, sectionSeatResponse2.getSeatResponses().get(0).getSeatId());
    }

    @Test
    void testGetSeatingPlanDetails() {
        GetSeatingPlanRequest request = new GetSeatingPlanRequest();
        request.setPlanId(1);

        SeatingPlan seatingPlan = new SeatingPlan();
        seatingPlan.setPlanId(1);
        seatingPlan.setPlanName("Test Plan");
        seatingPlan.setPlanRow(10);
        seatingPlan.setPlanCol(15);
        seatingPlan.setVenueId(1);

        Venue venue = new Venue();
        venue.setVenueId(1);
        venue.setVenueName("Test Venue");
        venue.setAddress("Test Address");

        SectionSeat sectionSeat = new SectionSeat();
        sectionSeat.setSectionId(1);
        sectionSeat.setSeatPrice(50.0);
        sectionSeat.setTotalSeats(100);
        sectionSeat.setSeatSectionDescription("VIP");
        sectionSeat.setSectionRow(5);
        sectionSeat.setSectionCol(10);
        sectionSeat.setNoSeatsLeft(50);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setSeatName("A1");
        seat.setSeatRow(1);
        seat.setSeatCol(1);
        seat.setSeatStatus(SeatStatus.available);

        when(seatingPlanRepository.findByPlanId(1)).thenReturn(seatingPlan);
        when(venueRepository.findByVenueId(1)).thenReturn(venue);
        when(sectionSeatRepository.findAllByPlanId(1)).thenReturn(List.of(sectionSeat));
        when(seatRepository.findAllBySectionId(1)).thenReturn(List.of(seat));

        GetSeatingPlanResponse response = seatingPlanService.getSeatingPlanDetails(request);

        // response
        assertEquals(1, response.getPlanId());
        assertEquals("Test Plan", response.getPlanName());
        assertEquals(10, response.getPlanRow());
        assertEquals(15, response.getPlanCol());
        assertEquals(1, response.getVenueId());
        assertEquals("Test Venue", response.getVenueName());
        assertEquals("Test Address", response.getAddress());

        // section seat details
        List<GetSectionSeatResponse> sectionSeats = response.getSectionSeatResponses();
        assertEquals(1, sectionSeats.size());
        GetSectionSeatResponse sectionSeatResponse = sectionSeats.get(0);
        assertEquals(50.0, sectionSeatResponse.getSeatPrice());
        assertEquals(100, sectionSeatResponse.getTotalSeats());
        assertEquals("VIP", sectionSeatResponse.getSeatSectionDescription());
        assertEquals(5, sectionSeatResponse.getSectionRow());
        assertEquals(10, sectionSeatResponse.getSectionCol());
        assertEquals(50, sectionSeatResponse.getNoSeatsLeft());

        // seat details
        List<Seat> seats = sectionSeatResponse.getSeatResponses();
        assertEquals(1, seats.size());
        Seat seatResponse = seats.get(0);
        assertEquals(1, seatResponse.getSeatId());
        assertEquals("A1", seatResponse.getSeatName());
        assertEquals(1, seatResponse.getSeatRow());
        assertEquals(1, seatResponse.getSeatCol());
        assertEquals(SeatStatus.available, seatResponse.getSeatStatus());
    }

}
