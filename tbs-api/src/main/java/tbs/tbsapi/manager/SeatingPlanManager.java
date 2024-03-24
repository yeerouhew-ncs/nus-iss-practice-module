package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.dto.AddSeatingPlanDto;
import tbs.tbsapi.dto.EditPlanSectionSeatDto;
import tbs.tbsapi.dto.EditSeatingPlanDto;
import tbs.tbsapi.service.SeatingPlanService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.AddSeatingPlanResponse;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class SeatingPlanManager {
    @Autowired
    SeatingPlanService seatingPlanService;

    public ResponseEntity<?> addSeatingPlan(AddSeatingPlanDto seatingPlanDto) {
        List<ValidationError> validationErrorList = seatingPlanDto.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: ADD SEATING PLAN VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }
        AddSeatingPlanResponse seatingPlanResponse = seatingPlanService.addSeatingPlan(seatingPlanDto);

        log.info("seatingPlanResponse {}", seatingPlanResponse);

        if(Objects.equals(seatingPlanResponse.getStatusCode(), "200") &&
                Objects.equals(seatingPlanResponse.getMessage(), "SUCCESS")) {
            log.info("END: ADD SEATING PLAN SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", seatingPlanResponse.getStatusCode(),
                    "message", "SUCCESS"));
        }
        log.info("END: ADD SEATING PLAN FAILURE");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "statusCode", "409",
                "message", "FAILURE"));
    }

    public ResponseEntity<?> editSeatingPlan(EditSeatingPlanDto seatingPlanDto) {
        List<ValidationError> validationErrorList = seatingPlanDto.validate();
        if(!validationErrorList.isEmpty()) {
            log.info("END: EDIT SEATING PLAN VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        List<String> response = seatingPlanService.editSeatingPlan(seatingPlanDto);
        if(Objects.equals(response.get(0), "200")) {
            log.info("END: EDIT SEATING PLAN SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", response.get(0),
                    "message", "SUCCESS"));
        } else {
            log.info("END: EDIT SEATING PLAN FAILED");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", response.get(0),
                    "message", response.get(1)));
        }
    }

    public ResponseEntity<?> editPlanCategory(EditPlanSectionSeatDto editPlanSectionSeatDto) {
        List<String> response = seatingPlanService.editPlanCategory(editPlanSectionSeatDto);
        if(Objects.equals(response.get(0), "200")) {
            log.info("END: EDIT SEATING PLAN SECTION SEATS SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", response.get(0),
                    "message", "SUCCESS"));
        } else {
            log.info("END: EDIT SEATING PLAN FAILED");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", response.get(0),
                    "message", response.get(1)));
        }
    }

    public ResponseEntity<?> getListOfSeatingPlans() {
        List<GetSeatingPlanResponse> seatingPlanResponseList = seatingPlanService.getListofSeatingPlans();

        log.info("END: GET LIST OF SEATING PLANS SUCCESSFUL");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode","200",
                "message", "SUCCESS",
                "seatingPlanList", seatingPlanResponseList
        ));
    }

    public ResponseEntity<?> getSeatingPlanDetails(GetSeatingPlanRequest getSeatingPlanRequest) {
        GetSeatingPlanResponse seatingPlanResponse = seatingPlanService.getSeatingPlanDetails(getSeatingPlanRequest);
        log.info("END: GET SEATING PLAN DETAILS SUCCESS");

        if(seatingPlanResponse.getPlanId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "SUCCESS",
                    "seatingPlanDetails", seatingPlanResponse
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "NO MATCHING SEATING PLAN"
            ));
        }
    }
}
