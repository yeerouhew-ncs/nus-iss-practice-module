package tbs.tbsapi.builder;

import tbs.tbsapi.domain.Queue;

import java.time.LocalDateTime;

public class QueueBuilder {
    private int queueId;
    private int subjectId;
    private int eventId;
    private String ticketnumber;
    private LocalDateTime queueDateTime;
    private String queueStatus;

    public QueueBuilder() {
        // You can initialize default values here if needed
    }

    public QueueBuilder queueId(int queueId) {
        this.queueId = queueId;
        return this;
    }

    public QueueBuilder subjectId(int subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    public QueueBuilder eventId(int eventId) {
        this.eventId = eventId;
        return this;
    }

    public QueueBuilder ticketnumber(String ticketnumber) {
        this.ticketnumber = ticketnumber;
        return this;
    }

    public QueueBuilder queueDateTime(LocalDateTime queueDateTime) {
        this.queueDateTime = queueDateTime;
        return this;
    }

    public QueueBuilder queueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
        return this;
    }

    public Queue build() {
        return new Queue(queueId, subjectId, eventId, ticketnumber, queueDateTime, queueStatus);
    }
}
