export interface IEventListRequest {
  eventId: string | null;
  eventName: string | null;
  artistName: string | null;
  eventFromDt: string | null;
  eventToDt: string | null;
  planId: string | null;
  usesrId: string | null;
  page: number;
}

export interface IEventListResponse {
  message: string;
  statusCode: string;
  eventList: {
    content: EventResponse[];
  };
}

export interface EventResponse {
  eventId: string;
  eventName: string;
  artistName: string;
  eventFromDt: string;
  eventToDt: string;
  planId: string;
  userId: string;
  eventType: string;
  genre: string;
}

export interface AddEventRequest {
  eventName: string;
  artistName: string;
  eventFromDt: string;
  eventToDt: string;
  planId: string;
  // subjectId: string;
}

export interface AddEventResponse {
  statusCode: string;
  message: string;
}

export interface EditEventResponse {
  statusCode: string;
  message: string;
}

export interface IGetEventDetailsRequest {
  eventId: string | undefined;
  subjectId: string | undefined;
}

export interface EventDetailsResponse {
  eventId: string;
  eventName: string;
  artistName: string;
  eventFromDt: string;
  eventToDt: string;
  planId: string;
  subjectId: string;
  eventType: string;
  genre: string;
  seatingPlanResponse: SeatingPlanResponse;
}

export interface SeatingPlanResponse {
  planId: string;
  planName: string;
  planRow: number;
  planCol: number;
  venueId: string;
  venueName: string;
  address: string;
  sectionSeatResponses: SectionSeatResponse[];
}

export interface SectionSeatResponse {
  sectionId: string;
  totalSeats: number;
  noSeatsLeft: number;
  seatPrice: number;
  seatSectionDescription: string;
  sectionRow: number;
  sectionCol: number;
  seatResponses: SeatResponse[];
}

export interface SeatResponse {
  seatId: string;
  seatName: string;
  seatStatus: string;
  seatRow: number;
  seatCol: number;
  sectionId: string;
}

export interface IGetEventDetailsResponse {
  message: string;
  statusCode: string;
  eventDetails: EventDetailsResponse;
}
