package tbs.tbsapi.manager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.apache.qpid.proton.amqp.messaging.Section;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.dto.*;
import tbs.tbsapi.service.SeatingPlanService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.AddSeatingPlanResponse;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

public class SeatingPlanManagerTest {

    @Test
    public void testAddSeatingPlan_ValidationFailed() {
        SeatingPlanService service = mock(SeatingPlanService.class);
        AddSeatingPlanDto dto = mock(AddSeatingPlanDto.class);

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("planName", "Plan name is required"));
        validationErrors.add(new ValidationError("planRow", "Plan row must be greater than zero"));

        when(dto.validate()).thenReturn(validationErrors);

        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.addSeatingPlan(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("422", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) response.getBody()).get("validationError"));
    }

    @Test
    public void testAddSeatingPlan_Success() {
        AddSeatingPlanDto dto = mock(AddSeatingPlanDto.class);
        SeatingPlanService service = mock(SeatingPlanService.class);
        AddSeatingPlanResponse r = new AddSeatingPlanResponse();
        r.setStatusCode("200");
        r.setMessage("SUCCESS");
        when(service.addSeatingPlan(dto)).thenReturn(r);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.addSeatingPlan(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAddSeatingPlan_Failed() {
        AddSeatingPlanDto dto = mock(AddSeatingPlanDto.class);
        SeatingPlanService service = mock(SeatingPlanService.class);

        when(service.addSeatingPlan(dto)).thenReturn(new AddSeatingPlanResponse());
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.addSeatingPlan(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("409", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("FAILURE", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testEditSeatingPlan_ValidationFailed() {
        SeatingPlanService service = mock(SeatingPlanService.class);
        EditSeatingPlanDto dto = mock(EditSeatingPlanDto.class);

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("planName", "Plan name is required"));
        validationErrors.add(new ValidationError("planRow", "Plan row must be greater than zero"));

        when(dto.validate()).thenReturn(validationErrors);

        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.editSeatingPlan(dto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("422", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((Map<?, ?>) response.getBody()).get("validationError"));
    }

    @Test
    public void testEditSeatingPlan_Success() {
        EditSeatingPlanDto dto = mock(EditSeatingPlanDto.class);
        dto.setPlanId(1); // Assuming planId 1 exists and is successfully updated
        SeatingPlanService service = mock(SeatingPlanService.class);
        List<String> r = new ArrayList<>();
        r.add("200");
        r.add("Plan updated successfully");
        when(service.editSeatingPlan(dto)).thenReturn(r);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.editSeatingPlan(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
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

    @Test
    public void testGetListOfSeatingPlans_Success() {

        //List<GetSeatingPlanResponse> seatingPlanResponseList = mock();
        List<GetSeatingPlanResponse> seatingPlanResponseList = getMockSeatingPlanResponseList();
        SeatingPlanService service = mock(SeatingPlanService.class);

        when(service.getListofSeatingPlans()).thenReturn(seatingPlanResponseList);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;
        
        ResponseEntity<?> response = manager.getListOfSeatingPlans();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(seatingPlanResponseList, ((Map<?, ?>) response.getBody()).get("seatingPlanList"));
    }

    private static List<GetSeatingPlanResponse> getMockSeatingPlanResponseList() {
        List<GetSeatingPlanResponse> seatingPlanResponseList = new ArrayList<>();

        GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
        seatingPlanResponse.setPlanId(1);
        seatingPlanResponse.setPlanName("Sample Plan 1");
        seatingPlanResponse.setPlanRow(10);
        seatingPlanResponse.setPlanCol(10);

        Venue venue = new Venue();
        venue.setVenueId(1);
        venue.setVenueName("Sample Venue 1");
        venue.setAddress("Sample Address 1");

        seatingPlanResponse.setVenueId(venue.getVenueId());
        seatingPlanResponse.setVenueName(venue.getVenueName());
        seatingPlanResponse.setAddress(venue.getAddress());

        seatingPlanResponseList.add(seatingPlanResponse);

        return seatingPlanResponseList;
    }

    @Test
    public void testGetSeatingPlanDetails_Success() {
        GetSeatingPlanRequest request = new GetSeatingPlanRequest();
        request.setPlanId(1);

        GetSeatingPlanResponse expectedResponse = getMockSeatingPlanResponse();
        SeatingPlanService service = mock(SeatingPlanService.class);
        when(service.getSeatingPlanDetails(request)).thenReturn(expectedResponse);
        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.getSeatingPlanDetails(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(expectedResponse, ((Map<?, ?>) response.getBody()).get("seatingPlanDetails"));
    }
    private static GetSeatingPlanResponse getMockSeatingPlanResponse() {
        GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
        seatingPlanResponse.setPlanId(1);
        seatingPlanResponse.setPlanName("Sample Plan");
        seatingPlanResponse.setPlanRow(10);
        seatingPlanResponse.setPlanCol(10);

        Venue venue = new Venue();
        venue.setVenueId(1);
        venue.setVenueName("Sample Venue");
        venue.setAddress("Sample Address");

        seatingPlanResponse.setVenueId(venue.getVenueId());
        seatingPlanResponse.setVenueName(venue.getVenueName());
        seatingPlanResponse.setAddress(venue.getAddress());

        return seatingPlanResponse;
    }
    @Test
    public void testGetSeatingPlanDetails_Fail() {
        GetSeatingPlanRequest request = new GetSeatingPlanRequest();
        request.setPlanId(123);
        SeatingPlanService service = mock(SeatingPlanService.class);

        GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
        seatingPlanResponse.setPlanId(null);
        when(service.getSeatingPlanDetails(request)).thenReturn(seatingPlanResponse); // Simulating failure to retrieve seating plan details

        SeatingPlanManager manager = new SeatingPlanManager();
        manager.seatingPlanService = service;

        ResponseEntity<?> response = manager.getSeatingPlanDetails(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", ((Map<?, ?>) response.getBody()).get("statusCode"));
        assertEquals("NO MATCHING SEATING PLAN", ((Map<?, ?>) response.getBody()).get("message"));
    }
}
