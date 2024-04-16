import {SeatInfo} from "seatchart";

export interface IOrderRequest{
    eventId:string|undefined;
    subjectId:string|undefined;
    seatNames: SeatInfo[] | undefined;
    orderDateTime: string;
    orderStatus: string;
    totalPrice: number|undefined;
}
export interface IOrderResponse {
    statusCode: string;
    message: string;
}
