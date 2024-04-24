import axios from "axios";
import {
  IOrderRequest,
  IOrderResponse,
} from "../../interfaces/order-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const ADD_TO_ORDER = API_DOMAIN + "api/order/add";

export const addOrder = async (
  orderRequest: IOrderRequest
): Promise<IOrderResponse> => {
  const { data } = await axios.post<IOrderResponse>(ADD_TO_ORDER, orderRequest);
  return data;
};
