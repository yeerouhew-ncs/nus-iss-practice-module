package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.domain.enums.SubjectStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse {
    private Integer subjectId;
    private String email;
    private String fullName;
    private SubjectRole subjectRole;
    private SubjectStatus status;
}
