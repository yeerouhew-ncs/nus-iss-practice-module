package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEventResponse {
    private Integer eventId;

    private String eventName;

    private String artistName;

    private LocalDateTime eventFromDt;

    private LocalDateTime eventToDt;

    private Integer subjectId;

    private Integer planId;
}
