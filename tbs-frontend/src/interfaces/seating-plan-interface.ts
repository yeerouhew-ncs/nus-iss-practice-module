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
  seatRow: number;
  seatCol: number;
}

export interface PlanList {
  planId: string;
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
