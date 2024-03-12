package tbs.tbsapi.domain;

import jakarta.persistence.*;
import lombok.*;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.domain.enums.SubjectStatus;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SUBJECT")
public class Subject {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SUBJECT_ID")
    private Integer subjectId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "SUBJECT_ROLE")
    @Enumerated(EnumType.STRING)
    private SubjectRole subjectRole;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private SubjectStatus status;
}
