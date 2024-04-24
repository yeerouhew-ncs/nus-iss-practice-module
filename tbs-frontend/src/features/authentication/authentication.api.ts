import axios from "axios";
import {
  ILoginRequest,
  ILoginResponse,
  IRegisterRequest,
  IRegisterResponse,
  IUserInfoResponse,
} from "../../interfaces/authentication-interface";

// const API_DOMAIN = "http://localhost:8081/";
const API_DOMAIN =
  "http://tbs-nlb-backend-b2e27a037c4d902e.elb.ap-southeast-1.amazonaws.com/";
const REGISTER_URL = API_DOMAIN + "api/auth/register";
const LOGIN_URL = API_DOMAIN + "api/auth/sign-in";
const USER_INFO_URL = API_DOMAIN + "api/subject/subject-details";

export const registerApi = async (
  registerRequest: IRegisterRequest
): Promise<IRegisterResponse> => {
  const { data } = await axios.post<IRegisterResponse>(
    REGISTER_URL,
    registerRequest
  );
  return data;
};

export const loginApi = async (
  loginRequest: ILoginRequest
): Promise<ILoginResponse> => {
  const { data } = await axios.post<ILoginResponse>(LOGIN_URL, loginRequest);
  return data;
};

export const getUserInfo = async (
  accessToken: string
): Promise<IUserInfoResponse> => {
  const { data } = await axios.post<IUserInfoResponse>(
    USER_INFO_URL,
    {},
    {
      headers: {
        Authorization: "Bearer " + accessToken,
      },
    }
  );

  return data;
};
