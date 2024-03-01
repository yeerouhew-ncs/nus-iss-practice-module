import axios from "axios";
import {
  AddEventRequest,
  AddEventResponse,
  IEventListRequest,
  IEventListResponse,
} from "../../interfaces/event-interface";

const API_DOMAIN = "http://localhost:8080/";
const GET_EVENT_LIST_URL = API_DOMAIN + "api/event/get-list";
const ADD_EVENT_URL = API_DOMAIN + "api/event/add";

export const createEventApi = async () => {
  return;
};

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
    addEventRequest, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

  return data;
};