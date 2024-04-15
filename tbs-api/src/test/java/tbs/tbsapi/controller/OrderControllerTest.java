package tbs.tbsapi.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.controller.OrderController;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.manager.OrderManager;
import tbs.tbsapi.validation.ValidationError;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {


    @Test
    public void testAddOrder() throws Exception {
        OrderController orderController = new OrderController();
        OrderManager orderManager = mock(OrderManager.class);
        orderController.orderManager = orderManager;
        when(orderManager.addOrder(any(AddOrderDto.class))).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> responseEntity = orderController.addOrder(new AddOrderDto());
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

}
