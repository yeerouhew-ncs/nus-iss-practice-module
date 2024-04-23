package tbs.tbsapi.factory;

import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.dto.AddSubjectDto;

public interface SubjectFactory {
    Subject addSubject(AddSubjectDto addSubjectDto);
}
