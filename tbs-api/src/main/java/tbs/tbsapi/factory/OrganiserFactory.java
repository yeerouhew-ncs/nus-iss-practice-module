package tbs.tbsapi.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.domain.enums.SubjectStatus;
import tbs.tbsapi.dto.AddSubjectDto;

@Component
public class OrganiserFactory {

    @Autowired
    PasswordEncoder passwordEncoder;

    Subject addSubject (AddSubjectDto addSubjectDto) {
        return Subject.builder()
                .email(addSubjectDto.getEmail())
                .fullName(addSubjectDto.getFullName())
                .subjectRole(SubjectRole.ORGANISER)
                .status(SubjectStatus.ACTIVE)
                .password(passwordEncoder.encode(addSubjectDto.getPassword()))
                .build();
    }
}
