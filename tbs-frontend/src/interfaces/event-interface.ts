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
