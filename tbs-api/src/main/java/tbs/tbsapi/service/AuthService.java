package tbs.tbsapi.service;

import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.vo.response.RegisterUserResponse;

public interface AuthService {
    RegisterUserResponse registerUser(AddSubjectDto addSubjectDto);
    RegisterUserResponse getUserByEmail(String email);
}
