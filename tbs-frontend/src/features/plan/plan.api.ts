import axios from "axios";
import { IVenueListResponse } from "../../interfaces/venue-interface";
import {
  IAddPlanRequest,
  IAddPlanResponse,
  IEditPlanCategoryRequest,
  IEditPlanRequest,
  IGetListOfPlanResponse,
  IGetPlanDetailsRequest,
  IGetPlanDetailsResponse,
} from "../../interfaces/seating-plan-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const GET_VENUE_LIST_URL = API_DOMAIN + "api/venue/get-list";
const ADD_SEATING_PLAN_URL = API_DOMAIN + "api/seating-plan/add";
const EDIT_SEATING_PLAN_URL = API_DOMAIN + "api/seating-plan/edit";
const EDIT_PLAN_CATEGORY_URL = API_DOMAIN + "api/seating-plan/edit-category";
const GET_SEATING_PLAN_LIST_URL = API_DOMAIN + "api/seating-plan/plan-list";
const GET_SEATING_PLAN_DETAILS_URL =
  API_DOMAIN + "api/seating-plan/plan-details";
const GET_PLAN_DETAILS_URL = API_DOMAIN + "api/seating-plan/plan-details";
const GET_LIST_OF_PLANS_URL = API_DOMAIN + "api/seating-plan/plan-list";

export const getVenueListApi = async (): Promise<IVenueListResponse> => {
  const { data } = await axios.post<IVenueListResponse>(GET_VENUE_LIST_URL);
  return data;
};

export const getPlanListApi = async (): Promise<IGetListOfPlanResponse> => {
  const { data } = await axios.post<IGetListOfPlanResponse>(
    GET_SEATING_PLAN_LIST_URL
  );
  return data;
};

export const getPlanDetailsApi = async (
  planDetailsRequest: IGetPlanDetailsRequest
): Promise<IGetPlanDetailsResponse> => {
  const { data } = await axios.post<IGetPlanDetailsResponse>(
    GET_SEATING_PLAN_DETAILS_URL,
    planDetailsRequest
  );

  return data;
};

export const addPlanApi = async (
  addPlanRequest: IAddPlanRequest
): Promise<IAddPlanResponse> => {
  const { data } = await axios.post<IAddPlanResponse>(
    ADD_SEATING_PLAN_URL,
    addPlanRequest,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return data;
};

export const editPlanApi = async (
  editPlanRequest: IEditPlanRequest
): Promise<IAddPlanResponse> => {
  const { data } = await axios.post<IAddPlanResponse>(
    EDIT_SEATING_PLAN_URL,
    editPlanRequest
  );
  return data;
};

export const editPlanCategoryApi = async (
  editPlanCategoryRequest: IEditPlanCategoryRequest
): Promise<IAddPlanResponse> => {
  const { data } = await axios.post<IAddPlanResponse>(
    EDIT_PLAN_CATEGORY_URL,
    editPlanCategoryRequest
  );
  return data;
};
