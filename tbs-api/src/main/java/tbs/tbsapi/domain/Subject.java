package tbs.tbsapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "SUBJECT_ROLE")
    private String subjectRole;

    @Column(name = "STATUS")
    private String status;
}
