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

import java.util.Optional;

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

        Subject saveSubject = subjectRepository.save(preSaveSubject);

        RegisterUserResponse userResponse = new RegisterUserResponse();
        userResponse.setEmail(saveSubject.getEmail());
        userResponse.setStatus(saveSubject.getStatus());
        userResponse.setSubjectId(saveSubject.getSubjectId());
        userResponse.setFullName(saveSubject.getFullName());
        userResponse.setSubjectRole(saveSubject.getSubjectRole());

        return userResponse;
    }

    public RegisterUserResponse getUserByEmail(String email) {

        Subject subject = subjectRepository.findByEmail(email);

        RegisterUserResponse userResponse = new RegisterUserResponse();

        if(subject != null) {
            userResponse.setEmail(subject.getEmail());
            userResponse.setStatus(subject.getStatus());
            userResponse.setSubjectId(subject.getSubjectId());
            userResponse.setFullName(subject.getFullName());
            userResponse.setSubjectRole(subject.getSubjectRole());
        }

        return userResponse;
    }
}
