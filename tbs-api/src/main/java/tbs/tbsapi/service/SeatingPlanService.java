package tbs.tbsapi.service;

import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

import java.util.List;

public interface SeatingPlanService {
    List<GetSeatingPlanResponse> getListofSeatingPlans();
    GetSeatingPlanResponse getSeatingPlanDetails(GetSeatingPlanRequest getSeatingPlanRequest);
}
