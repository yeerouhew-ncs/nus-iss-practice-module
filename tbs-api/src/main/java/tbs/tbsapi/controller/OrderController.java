package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.manager.OrderManager;

@Log4j2
@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderManager orderManager;
    @GetMapping(value="/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("Status: OK");
    }
    @PostMapping(path = "/add")
    public ResponseEntity<?> addOrder(@RequestBody AddOrderDto addOrderDto) throws Exception{
        log.info("START: ADD ORDER");
        return orderManager.addOrder(addOrderDto);
    }
}
