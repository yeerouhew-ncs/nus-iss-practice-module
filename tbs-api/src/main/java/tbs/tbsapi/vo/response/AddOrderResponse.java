package tbs.tbsapi.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderResponse {

    private String message;

    private String statusCode;

    private Integer orderId;

    private Integer eventId;

    private LocalDateTime orderDateTime;

    private String orderStatus;

    private Integer subjectId;

    private Double totalPrice;
}
