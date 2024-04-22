package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.service.OrderService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.response.AddOrderResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class OrderManager {
    @Autowired
    private OrderService orderService;

    public ResponseEntity<?>  addOrder(AddOrderDto addOrderDto) {
        List<ValidationError> validationErrorList = addOrderDto.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: ADD ORDER VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        AddOrderResponse addOrderResponse = orderService.addOrder(addOrderDto);

        if(Objects.equals(addOrderResponse.getStatusCode(), "200") && Objects.equals(addOrderResponse.getMessage(), "SUCCESS")) {
            log.info("END: ADD ORDER SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", addOrderResponse.getStatusCode(),
                    "message", "SUCCESS"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "statusCode", "409",
                    "message", "FAILURE"));
        }
    }
}
