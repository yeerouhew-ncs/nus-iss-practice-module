package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectStatus;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.repository.SubjectRepository;
import tbs.tbsapi.vo.response.RegisterUserResponse;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public RegisterUserResponse registerUser(AddSubjectDto addSubjectDto) {
        Subject preSaveSubject = Subject.builder()
                .email(addSubjectDto.getEmail())
                .fullName(addSubjectDto.getFullName())
                .subjectRole(addSubjectDto.getSubjectRole())
                .status(SubjectStatus.ACTIVE)
                .password(passwordEncoder.encode(addSubjectDto.getPassword()))
                .build();

        // TODO: check if user exists

        Subject saveSubject = subjectRepository.save(preSaveSubject);

        RegisterUserResponse userResponse = new RegisterUserResponse();
        userResponse.setEmail(saveSubject.getEmail());
        userResponse.setStatus(saveSubject.getStatus());
        userResponse.setSubjectId(saveSubject.getSubjectId());
        userResponse.setFullName(saveSubject.getFullName());
        userResponse.setSubjectRole(saveSubject.getSubjectRole());

        return userResponse;
    }
}
