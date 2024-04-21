package tbs.tbsapi.manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.service.OrderService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.response.AddOrderResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderManagerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderManager orderManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddOrder_Success() {
        AddOrderDto addOrderDto = new AddOrderDto();
        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setStatusCode("200");
        addOrderResponse.setMessage("SUCCESS");
        when(orderService.addOrder(addOrderDto)).thenReturn(addOrderResponse);

        ResponseEntity<?> response = orderManager.addOrder(addOrderDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAddOrder_ValidationFailed() {
        AddOrderDto addOrderDto = mock(AddOrderDto.class);
        List<ValidationError> validationErrors = Collections.singletonList(new ValidationError("field", "error message"));
        when(addOrderDto.validate()).thenReturn(validationErrors);

        ResponseEntity<?> response = orderManager.addOrder(addOrderDto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("VALIDATION FAILED", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(validationErrors, ((List<?>) ((Map<?, ?>) response.getBody()).get("validationError")));
    }


    @Test
    public void testAddOrder_Failure() {
        AddOrderDto addOrderDto = new AddOrderDto();
        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setStatusCode("400");
        addOrderResponse.setMessage("FAILURE");
        when(orderService.addOrder(addOrderDto)).thenReturn(addOrderResponse);

        ResponseEntity<?> response = orderManager.addOrder(addOrderDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("FAILURE", ((Map<?, ?>) response.getBody()).get("message"));
    }
}
