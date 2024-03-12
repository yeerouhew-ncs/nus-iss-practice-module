export interface IRegisterRequest {
  email: string;
  password: string;
  fullName: string;
  subjectRole: string;
}

export interface IRegisterResponse {
  message: string;
  statusCode: string;
  subjectDetails: UserResponse;
}

export interface UserResponse {
  subjectId: string;
  email: string;
  fullName: string;
  subjectRole: string;
  status: string;
}
