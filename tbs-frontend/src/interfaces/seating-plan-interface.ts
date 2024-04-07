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
  seatResponses: GetSeatResponse[];
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
  seatRow: number;
  seatCol: number;
}

export interface GetSeatResponse {
  seatId: string;
  seatName: string;
  seatStatus: string;
  sectionId: string;
  seatRow: number;
  seatCol: number;
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

export interface EditSeatReq {
  seatId: string;
  seatName: string;
  seatRow: number;
  seatCol: number;
}

export interface EditSectionSeatReq extends Omit<SectionSeatReq, "seats"> {
  sectionId: string;
  seats: EditSeatReq[];
}

export interface IEditPlanCategoryRequest {
  planId: string;
  sectionSeats: EditSectionSeatReq[];
}
