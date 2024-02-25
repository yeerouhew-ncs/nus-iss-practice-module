import axios from "axios";
import {
  IEventListRequest,
  IEventListResponse,
} from "../../interfaces/event-interface";

const API_DOMAIN = "http://localhost:8080/";
const GET_EVENT_LIST_URL = API_DOMAIN + "api/event/get-list";

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
