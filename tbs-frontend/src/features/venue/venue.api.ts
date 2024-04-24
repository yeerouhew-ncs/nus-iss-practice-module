import axios from "axios";

import {
  IVenueListResponse,
  IVenueRequest,
} from "../../interfaces/venue-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const VENUE_API_MAPPING = API_DOMAIN + "api/venue";

const SEARCH_VENUE_URL = VENUE_API_MAPPING + "/search";

export const searchVenueAPI = async (
  venueRequest: IVenueRequest
): Promise<IVenueListResponse> => {
  const { data } = await axios.post<IVenueListResponse>(
    SEARCH_VENUE_URL,
    venueRequest
  );

  return data;
};
