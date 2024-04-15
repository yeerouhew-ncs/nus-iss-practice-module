package tbs.tbsapi.controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.manager.EventManager;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventManager eventManager;

    public EventControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEvent() throws Exception {
        when(eventManager.addEvent(any(AddEventDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = eventController.addEvent(new AddEventDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGetListOfEvents() throws Exception {
        when(eventManager.getListOfEvents(any(GetListOfEventRequest.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = eventController.getListOfEvents(new GetListOfEventRequest());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGetEventDetails() throws Exception {
        when(eventManager.getEventDetails(any(GetEventRequest.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = eventController.getEventDetails(new GetEventRequest());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testEditEvent() throws Exception {
        when(eventManager.editEvent(any(EditEventDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = eventController.editEvent(new EditEventDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }
}
