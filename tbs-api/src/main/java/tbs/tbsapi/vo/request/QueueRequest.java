package tbs.tbsapi.vo.request;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class QueueRequest {
    @Setter
    private int eventId;
    @Setter
    private int subjectId;
    @Setter
    private String ticketnumber;

    public QueueRequest(Integer eventId,Integer subjectId){
        this.eventId = eventId;
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "QueueRequest{" +
                "eventId=" + eventId +
                ", subjectId=" + subjectId +
                ", ticketnumber=" + ticketnumber +
                '}';
    }
}
