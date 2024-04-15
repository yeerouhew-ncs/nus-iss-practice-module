package tbs.tbsapi.manager;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.domain.enums.EventType;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.dto.EditPlanSectionSeatDto;
import tbs.tbsapi.dto.EditSeatingPlanDto;
import tbs.tbsapi.service.EventService;
import tbs.tbsapi.service.SeatingPlanService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.EventDetailsResponse;
import tbs.tbsapi.vo.response.GetEventResponse;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventManagerTest {

    @Test
    public void testAddEvent_ValidationFailed() {
        AddEventDto dto = mock(AddEventDto.class);
        EventService service = mock(EventService.class);

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("Event Name", "Event name is required"));

        when(dto.validate()).thenReturn(validationErrors);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.addEvent(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("422", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) response.getBody()).get("validationError"));
    }

    @Test
    public void testAddConcertEvent_Success() {
        AddEventDto dto = new AddEventDto();
        dto.setEventType(EventType.CONCERT);
        EventService service = mock(EventService.class);
        AddEventResponse r = new AddEventResponse();
        r.setStatusCode("200");
        r.setMessage("SUCCESS");
        r.setEventType(EventType.CONCERT);
        when(service.addConcert(dto)).thenReturn(r);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.addEvent(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAddConcertEvent_Failure() {
        AddEventDto dto = new AddEventDto();
        dto.setEventType(EventType.CONCERT);
        EventService service = mock(EventService.class);
        AddEventResponse r = new AddEventResponse();
        r.setEventType(EventType.CONCERT);
        r.setStatusCode("200");
        when(service.addConcert(dto)).thenReturn(r);
        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.addEvent(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("409", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("FAILURE", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAddSportsEvent_Success() {
        AddEventDto dto = new AddEventDto();
        dto.setEventType(EventType.SPORTS);
        EventService service = mock(EventService.class);
        AddEventResponse r = new AddEventResponse();
        r.setStatusCode("200");
        r.setMessage("SUCCESS");
        r.setEventType(EventType.SPORTS);
        when(service.addSportsEvent(dto)).thenReturn(r);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.addEvent(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAddSportsEvent_Failure() {
        AddEventDto dto = new AddEventDto();
        dto.setEventType(EventType.SPORTS);
        EventService service = mock(EventService.class);
        AddEventResponse r = new AddEventResponse();
        r.setEventType(EventType.SPORTS);
        r.setStatusCode("200");
        when(service.addSportsEvent(dto)).thenReturn(r);
        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.addEvent(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("409", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("FAILURE", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testGetListOfEvents_ValidationFail() {
        GetListOfEventRequest request = mock(GetListOfEventRequest.class);
        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("eventName", "Event name is required"));

        when(request.validate()).thenReturn(validationErrors);

        EventManager manager = new EventManager();

        ResponseEntity<?> response = manager.getListOfEvents(request);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("422", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) response.getBody()).get("validationError"));

    }

    @Test
    public void testGetListOfEvents_Success() {
        GetListOfEventRequest request = new GetListOfEventRequest();
        request.setPage(0);

        EventService service = mock(EventService.class);
        Page<GetEventResponse> page = new PageImpl<>(Collections.singletonList(new GetEventResponse()));
        when(service.getListOfEvents(any(), any())).thenReturn(page);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.getListOfEvents(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(page, ((Map<?, ?>) response.getBody()).get("eventList"));
    }

    @Test
    public void testGetEventDetails_Success() {
        GetEventRequest request = new GetEventRequest();
        request.setEventId(1);

        EventDetailsResponse expectedResponse = getMockEventDetailsResponse();
        EventService service = mock(EventService.class);
        when(service.getEventDetails(request)).thenReturn(expectedResponse);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.getEventDetails(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(expectedResponse, ((Map<?, ?>) response.getBody()).get("eventDetails"));
    }
    private EventDetailsResponse getMockEventDetailsResponse() {
        EventDetailsResponse response = new EventDetailsResponse();

        // Populate the response with mock data
        response.setEventId(1);
        response.setEventName("Mock Event");
        response.setEventFromDt(LocalDateTime.now());
        response.setArtistName("Mock Artist");
        response.setPlanId(1);
        response.setEventToDt(LocalDateTime.now().plusHours(2));
        response.setSubjectId(1);

        return response;
    }

    @Test
    public void testGetEventDetails_Fail() {
        GetEventRequest request = new GetEventRequest();
        request.setEventId(1);
        EventService service = mock(EventService.class);

        EventDetailsResponse eventResponse = new EventDetailsResponse();
        eventResponse.setEventId(null);
        when(service.getEventDetails(request)).thenReturn(eventResponse);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.getEventDetails(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("NO MATCHING EVENT", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditEvent_ValidationFailed() {
        EditEventDto dto = mock(EditEventDto.class);
        EventService service = mock(EventService.class);

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("eventName", "Event name is required"));

        when(dto.validate()).thenReturn(validationErrors);

        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.editEvent(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("422", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) response.getBody()).get("validationError"));
    }

    @Test
    public void testEditEvent_Success() {
        EditEventDto dto = mock(EditEventDto.class);
        dto.setEventId(1); // Assuming eventId 1 exists and is successfully updated
        EventService service = mock(EventService.class);
        List<String> r = new ArrayList<>();
        r.add("200");
        r.add("Event updated successfully");
        when(service.editEvent(dto)).thenReturn(r);
        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.editEvent(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditEvent_Fail() {
        EditEventDto dto = new EditEventDto();
        dto.setEventId(999);
        EventService service = mock(EventService.class);
        List<String> r = new ArrayList<>();
        r.add("400");
        r.add("Event not updated");
        when(service.editEvent(dto)).thenReturn(r);
        EventManager manager = new EventManager();
        manager.eventService = service;

        ResponseEntity<?> response = manager.editEvent(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("400", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("Event not updated", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditSeatingPlan_Fail() {
        EditSeatingPlanDto dto = new EditSeatingPlanDto();
        dto.setPlanId(999);
        SeatingPlanService service = mock(SeatingPlanService.class);
        List<String> r = new ArrayList<>();
        r.add("400");
        r.add("Plan not updated");
        when(service.editSeatingPlan(dto)).thenReturn(r);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.editSeatingPlan(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("400", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("Plan not updated", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditPlanCategory_Success() {

        EditPlanSectionSeatDto dto = new EditPlanSectionSeatDto();
        SeatingPlanService service = mock(SeatingPlanService.class);
        when(service.editPlanCategory(dto)).thenReturn(List.of("200", "SUCCESS"));
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;


        ResponseEntity<?> response = manager.editPlanCategory(dto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditPlanCategory_Fail() {
        EditPlanSectionSeatDto dto = new EditPlanSectionSeatDto();
        dto.setPlanId(999);
        SeatingPlanService service = mock(SeatingPlanService.class);
        List<String> r = new ArrayList<>();
        r.add("400");
        r.add("Plan not updated");
        when(service.editPlanCategory(dto)).thenReturn(r);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.editPlanCategory(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("400", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("Plan not updated", ((Map<?, ?>) response.getBody()).get("message"));
    }


}
