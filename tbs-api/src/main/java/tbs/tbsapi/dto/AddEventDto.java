package tbs.tbsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class AddEventDto extends CreatedUpdatedColumn implements Serializable {
    @JsonProperty("eventName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String eventName;

    @JsonProperty("artistName")
    @Size(max = 100, message = "Maximum size is 100 characters")
    private String artistName;

    @JsonProperty("eventFromDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventFromDt;

    @JsonProperty("eventToDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventToDt;

    @JsonProperty("planId")
    private Integer planId;

    @JsonProperty("subjectId")
    private Integer subjectId;
}
