package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.service.SeatingPlanService;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class SeatingPlanManager {
    @Autowired
    SeatingPlanService seatingPlanService;

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
