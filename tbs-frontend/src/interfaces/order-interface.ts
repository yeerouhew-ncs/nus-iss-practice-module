import {SeatInfo} from "seatchart";

export interface IOrderRequest{
    eventId:string|undefined;
    subjectId:string|undefined;
    seatNames: string[] | undefined;
    orderDt: string;
    orderStatus: string;
    totalPrice: Number|undefined;
}
export interface IOrderResponse {
    statusCode: string;
    message: string;
}
