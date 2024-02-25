package tbs.tbsapi.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListOfEventRequest {
    private Integer eventId;

    @Size(max = 100, message = "Maximum size is 100 characters")
    private String eventName;

    @Size(max = 100, message = "Maximum size is 100 characters")
    private String artistName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventFromDt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventToDt;

    private Integer subjectId;

    @NotNull(message = "This field is required")
    private Integer page;
}
