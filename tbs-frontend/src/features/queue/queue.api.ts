import axios from "axios";
import {
  IQueueRequest,
  IQueueRequest2,
  IQueueResponse,
  IQueueResponse2,
} from "../../interfaces/queue-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const JOIN_QUEUE_URL = API_DOMAIN + "api/queue/event";
export const CHECK_QUEUE_URL = API_DOMAIN + "api/queue/check-queue";

export const joinQueue = async (
  queueRequest: IQueueRequest
): Promise<IQueueResponse> => {
  const { data } = await axios.post<IQueueResponse>(
    JOIN_QUEUE_URL,
    queueRequest
  );
  return data;
};
