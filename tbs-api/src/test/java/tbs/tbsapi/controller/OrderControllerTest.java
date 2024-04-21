package tbs.tbsapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.manager.OrderManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Test
    public void testHealthEndpoint() {
        OrderController orderController = new OrderController();
        ResponseEntity<String> response = orderController.health();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status: OK", response.getBody());
    }

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
