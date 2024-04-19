import axios from "axios";
import {IOrderRequest, IOrderResponse} from "../../interfaces/order-interface";


const API_DOMAIN = "http://localhost:8081/";
const ADD_TO_ORDER = API_DOMAIN + "api/order/add";

export const addOrder = async (orderRequest: IOrderRequest): Promise<IOrderResponse> => {
    const { data } = await axios.post<IOrderResponse>(ADD_TO_ORDER,orderRequest);
    return data;
};