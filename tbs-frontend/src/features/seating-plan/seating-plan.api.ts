import axios from "axios";
import {
  IGetListOfPlanResponse,
  IGetPlanDetailsRequest,
  IGetPlanDetailsResponse,
} from "../../interfaces/seating-plan-interface";

const API_DOMAIN = "http://localhost:8080/";
const GET_PLAN_DETAILS_URL = API_DOMAIN + "api/seating-plan/plan-details";
const GET_LIST_OF_PLANS_URL = API_DOMAIN + "api/seating-plan/plan-list";

export const getSeatingPlanDetailsApi = async (
  getPlanDetailsRequest: IGetPlanDetailsRequest
): Promise<IGetPlanDetailsResponse> => {
  const { data } = await axios.post<IGetPlanDetailsResponse>(
    GET_PLAN_DETAILS_URL,
    getPlanDetailsRequest
  );
  return data;
};

export const getSeatingPlanListApi =
  async (): Promise<IGetListOfPlanResponse> => {
    const { data } = await axios.post<IGetListOfPlanResponse>(
      GET_LIST_OF_PLANS_URL
    );
    return data;
  };
