export interface IVenueListResponse {
  message: string;
  statusCode: string;
  venueList: VenueResponse[];
}

export interface VenueResponse {
  venueId: number;
  venueName: string;
  venueAddress: string;
}
