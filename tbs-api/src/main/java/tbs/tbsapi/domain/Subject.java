package tbs.tbsapi.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SUBJECT_ID")
    private Integer subjectId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "SUBJECT_ROLE")
    private String subjectRole;

    @Column(name = "STATUS")
    private String status;
}
