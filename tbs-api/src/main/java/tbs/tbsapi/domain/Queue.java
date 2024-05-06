package tbs.tbsapi.domain;

import jakarta.persistence.*;
import lombok.*;
import tbs.tbsapi.builder.QueueBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "QUEUE")
public class Queue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "QUEUE_ID")
    private int queueId;

    @Column(name = "SUBJECT_ID")
    private int subjectId;

    @Column(name = "EVENT_ID")
    private int eventId;


    @Column(name = "TICKET_NUMBER")
    private String ticketnumber;

    @Column(name = "QUEUE_DT")
    private LocalDateTime queueDateTime;

    @Column(name = "QUEUE_STATUS")
    private String queueStatus;

    public static QueueBuilder builder() {
        return new QueueBuilder();
    }
}
