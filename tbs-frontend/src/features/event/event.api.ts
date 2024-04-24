import axios from "axios";
import {
  AddEventRequest,
  AddEventResponse,
  EditEventResponse,
  IGetEventDetailsRequest,
  IEventListRequest,
  IEventListResponse,
  EventResponse,
  IGetEventDetailsResponse,
} from "../../interfaces/event-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const GET_EVENT_LIST_URL = API_DOMAIN + "api/event/get-list";
const ADD_EVENT_URL = API_DOMAIN + "api/event/add";
const EDIT_EVENT_URL = API_DOMAIN + "api/event/edit";
const GET_EVENT_DETAILS_URL = API_DOMAIN + "api/event/event-details";

export const getEventListApi = async (
  eventListRequest: IEventListRequest
): Promise<IEventListResponse> => {
  const { data } = await axios.post<IEventListResponse>(
    GET_EVENT_LIST_URL,
    eventListRequest
  );

  return data;
};

export const addEventApi = async (
  addEventRequest: string
): Promise<AddEventResponse> => {
  const { data } = await axios.post<AddEventResponse>(
    ADD_EVENT_URL,
    addEventRequest,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  return data;
};

export const editEventApi = async (
  accessToken: string | null,
  editEventRequest: string
): Promise<EditEventResponse> => {
  const { data } = await axios.post<EditEventResponse>(
    EDIT_EVENT_URL,
    editEventRequest,
    {
      headers: {
        Authorization: "Bearer " + accessToken,
        "Content-Type": "application/json",
      },
    }
  );

  return data;
};

export const getEventDetailsApi = async (
  getEventRequest: IGetEventDetailsRequest
): Promise<IGetEventDetailsResponse> => {
  const { data } = await axios.post<IGetEventDetailsResponse>(
    GET_EVENT_DETAILS_URL,
    getEventRequest
  );
  return data;
};
