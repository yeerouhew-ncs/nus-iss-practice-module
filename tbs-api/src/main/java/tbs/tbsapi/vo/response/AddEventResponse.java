package tbs.tbsapi.vo.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEventResponse {
    private String message;

    private String statusCode;

    private Integer eventId;

    private String eventName;

    private String artistName;

    private LocalDateTime eventFromDt;

    private LocalDateTime eventToDt;

    private Integer planId;

    private Integer subjectId;
}
