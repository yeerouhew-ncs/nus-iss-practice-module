package tbs.tbsapi.service;

import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.dto.AddSeatingPlanDto;
import tbs.tbsapi.dto.EditSeatingPlanDto;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.AddSeatingPlanResponse;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

import java.util.List;

public interface SeatingPlanService {

    AddSeatingPlanResponse addSeatingPlan(AddSeatingPlanDto seatingPlanDto);
    List<String> editSeatingPlan(EditSeatingPlanDto seatingPlanDto);
    List<GetSeatingPlanResponse> getListofSeatingPlans();
    GetSeatingPlanResponse getSeatingPlanDetails(GetSeatingPlanRequest getSeatingPlanRequest);
}
