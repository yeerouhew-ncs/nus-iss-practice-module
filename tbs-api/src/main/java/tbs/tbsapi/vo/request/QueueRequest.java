package tbs.tbsapi.vo.request;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class QueueRequest {
    @Setter
    private Integer eventId;
    @Setter
    private Integer subjectId;
    @Setter
    private Integer ticketnumber;

    public QueueRequest(Integer eventId,Integer subjectId){
        this.eventId = eventId;
        this.subjectId = subjectId;
    }
}
