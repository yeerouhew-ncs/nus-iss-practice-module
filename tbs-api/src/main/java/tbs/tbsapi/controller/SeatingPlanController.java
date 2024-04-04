package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddSeatingPlanDto;
import tbs.tbsapi.dto.EditPlanSectionSeatDto;
import tbs.tbsapi.dto.EditSeatingPlanDto;
import tbs.tbsapi.manager.SeatingPlanManager;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;

@Log4j2
@RestController
@RequestMapping("api/seating-plan")
@CrossOrigin(origins = "*")
public class SeatingPlanController {
    @Autowired
    SeatingPlanManager seatingPlanManager;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addSeatingPlan(@RequestBody AddSeatingPlanDto seatingPlanDto) {
        log.info("START: ADD SEATING PLAN");
        return seatingPlanManager.addSeatingPlan(seatingPlanDto);
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<?> editSeatingPlan(@RequestBody EditSeatingPlanDto seatingPlanDto) {
        log.info("START: EDIT SEATING PLAN");
        log.info("seatingPlanDto {} ", seatingPlanDto);
        return seatingPlanManager.editSeatingPlan(seatingPlanDto);
    }

    @PostMapping(path = "/edit-category")
    public ResponseEntity<?> editPlanCategory(@RequestBody EditPlanSectionSeatDto editPlanSectionSeatDto) {
        log.info("START: EDIT SEATING PLAN SECTION SEATS");
        return seatingPlanManager.editPlanCategory(editPlanSectionSeatDto);
    }

    @PostMapping(path = "/plan-details")
    public ResponseEntity<?> getSeatingPlan(@RequestBody GetSeatingPlanRequest getSeatingPlanRequest) throws Exception{
        log.info("START: GET SEATING PLAN DETAILS");
        return seatingPlanManager.getSeatingPlanDetails(getSeatingPlanRequest);
    }

    @PostMapping(path = "/plan-list")
    public ResponseEntity<?> getListOfPlans() {
        log.info("START: GET LIST OF SEATING PLANS");
        return seatingPlanManager.getListOfSeatingPlans();
    }
}
