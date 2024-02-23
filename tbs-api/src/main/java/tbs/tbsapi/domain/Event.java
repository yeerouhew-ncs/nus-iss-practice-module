package tbs.tbsapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "ARTIST_NAME")
    private String artistName;

    @Column(name = "EVENT_FROM_DT")
    private LocalDateTime eventFromDt;

    @Column(name = "EVENT_TO_DT")
    private LocalDateTime eventToDt;

    @Column(name = "PLAN_ID")
    private Integer planId;

    @Column(name = "SUBJECT_ID")
    private Integer subjectId;
}
