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

export interface ILoginRequest {
  email: string;
  password: string;
}

export interface ILoginResponse {
  message: string;
  statusCode: string;
  jwtDetails: JwtResponse;
}

export interface JwtResponse {
  accessToken: string;
}

export interface IUserInfoResponse {
  message: string;
  statusCode: string;
  userDetails: UserDetails;
  subjectDetails: SubjectDetails;
}

export interface SubjectDetails {
  subjectId: string;
  email: string;
  fullName: string;
  status: string;
  subjectRole: string;
}

export interface UserDetails {
  id: string;
  email: string;
  fullName: string;
  authorities: [
    {
      authority: string;
    }
  ];
}
