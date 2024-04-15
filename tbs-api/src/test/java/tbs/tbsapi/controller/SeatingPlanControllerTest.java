package tbs.tbsapi.controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.dto.AddSeatingPlanDto;
import tbs.tbsapi.dto.EditPlanSectionSeatDto;
import tbs.tbsapi.dto.EditSeatingPlanDto;
import tbs.tbsapi.manager.SeatingPlanManager;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SeatingPlanControllerTest {

    @InjectMocks
    private SeatingPlanController seatingPlanController;

    @Mock
    private SeatingPlanManager seatingPlanManager;

    public SeatingPlanControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSeatingPlan() {
        when(seatingPlanManager.addSeatingPlan(any(AddSeatingPlanDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = seatingPlanController.addSeatingPlan(new AddSeatingPlanDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testEditSeatingPlan() {
        when(seatingPlanManager.editSeatingPlan(any(EditSeatingPlanDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = seatingPlanController.editSeatingPlan(new EditSeatingPlanDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testEditPlanCategory() {
        when(seatingPlanManager.editPlanCategory(any(EditPlanSectionSeatDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = seatingPlanController.editPlanCategory(new EditPlanSectionSeatDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGetSeatingPlan() throws Exception {
        when(seatingPlanManager.getSeatingPlanDetails(any(GetSeatingPlanRequest.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = seatingPlanController.getSeatingPlan(new GetSeatingPlanRequest());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGetListOfPlans() {
        when(seatingPlanManager.getListOfSeatingPlans()).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = seatingPlanController.getListOfPlans();
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }
}
