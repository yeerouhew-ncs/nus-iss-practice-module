export interface IGetPlanDetailsRequest {
  planId: string | undefined;
  venueId: string | undefined;
}

export interface IGetPlanDetailsResponse {
  message: string;
  statusCode: string;
  seatingPlanDetails: PlanDetails;
}

export interface PlanDetails {
  planId: string;
  planName: string;
  planRow: number;
  planCol: number;
  venueId: string;
  venueName: string;
  address: string;
  sectionSeatResponses: SectionSeat[];
}

interface SectionSeat {
  sectionId: string;
  planId: string;
  totalSeats: number;
  noSeatsLeft: number;
  seatPrice: number;
  seatSectionDescription: string;
  sectionRow: number;
  sectionCol: number;
}

export interface PlanList {
  planId: string;
  planName: string;
  planRow: number;
  planCol: number;
  venueId: string;
  venueName: string;
  address: string;
  sectionSeatResponses: SectionSeat[];
}

export interface IGetListOfPlanResponse {
  message: string;
  statusCode: string;
  seatingPlanList: PlanList[];
}

export interface IAddPlanRequest {
  venueId: string;
  planName: string;
  planRow: number;
  planCol: number;
  sectionSeats: SectionSeatReq[];
}

export interface SectionSeatReq {
  totalSeats: number;
  sectionDesc: string;
  sectionRow: number;
  sectionCol: number;
  seatPrice: number;
  seats: SeatReq[];
}

export interface SeatReq {
  seatName: string;
}

export interface IAddPlanResponse {
  statusCode: string;
  message: string;
}

export interface IEditPlanRequest {
  planId: string;
  venueId: string;
  planName: string;
  planRow: number;
  planCol: number;
  sectionSeats: EditSectionSeatReq[];
}

export interface EditSectionSeatReq extends Omit<SectionSeatReq, "seats"> {
  sectionId: string;
  seats: SeatReq[];
}

// export interface EditSeatReq extends SeatReq {
//   seatId: string;
// }
