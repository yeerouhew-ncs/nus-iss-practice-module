package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbs.tbsapi.domain.enums.ResultEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponse {

    private String statusCode;
    private ResultEnum message;
    private String ticketnumber;


}
