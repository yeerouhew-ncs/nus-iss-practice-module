package tbs.tbsapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "EVENT_ID")
    private String eventId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "ARTIST_NAME")
    private String artistName;

    @Column(name = "EVENT_FROM_DT")
    private LocalDateTime eventFromDt;

    @Column(name = "EVENT_TO_DT")
    private LocalDateTime eventToDt;

    @Column(name = "PLAN_ID")
    private String planId;

    @Column(name = "USER_ID")
    private String userId;
}
