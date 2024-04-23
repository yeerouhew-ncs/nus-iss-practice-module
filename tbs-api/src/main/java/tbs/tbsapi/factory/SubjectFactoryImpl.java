package tbs.tbsapi.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.dto.AddSubjectDto;

@Component
public class SubjectFactoryImpl implements SubjectFactory{
    @Autowired
    UserFactory userFactory;
    @Autowired
    OrganiserFactory organiserFactory;
    @Autowired
    AdminFactory adminFactory;
    @Override
    public Subject addSubject(AddSubjectDto addSubjectDto) {
        SubjectRole role = addSubjectDto.getSubjectRole();

        return switch (role) {
            case MOP -> userFactory.addSubject(addSubjectDto);
            case ORGANISER -> organiserFactory.addSubject(addSubjectDto);
            case ADMIN -> adminFactory.addSubject(addSubjectDto);
            default -> throw new IllegalArgumentException("Unknown user role: " + role);
        };
    }
}
