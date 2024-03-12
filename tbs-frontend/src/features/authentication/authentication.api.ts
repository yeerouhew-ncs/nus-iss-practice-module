import axios from "axios";
import {
  IRegisterRequest,
  IRegisterResponse,
} from "../../interfaces/authentication-interface";

const API_DOMAIN = "http://localhost:8080/";
const REGISTER_URL = API_DOMAIN + "api/auth/register";

export const loginApi = async () => {
  return;
};

export const registerApi = async (
  registerRequest: IRegisterRequest
): Promise<IRegisterResponse> => {
  const { data } = await axios.post<IRegisterResponse>(
    REGISTER_URL,
    registerRequest
  );
  return data;
};
