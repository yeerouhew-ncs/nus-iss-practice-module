package tbs.tbsapi.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Singapore")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "QueueRequest{" +
                "eventId=" + eventId +
                ", subjectId=" + subjectId +
                ", ticketnumber='" + ticketnumber + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public QueueRequest(Integer eventId, Integer subjectId){
        this.eventId = eventId;
        this.subjectId = subjectId;
    }


}
