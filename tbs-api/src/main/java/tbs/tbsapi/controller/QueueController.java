package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.manager.QueueManager;
import tbs.tbsapi.service.RabbitMQProducer;
@Log4j2
@RestController
@RequestMapping("api/queue")
@CrossOrigin(origins = "*")
public class QueueController {

    private final RabbitMQProducer rabbitMQProducer;

    public QueueController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }
//    @GetMapping(value ="/event/{eventid}")
//    public void joinQueue(@PathVariable("eventid") String eventid){
////        queueManager.
////        return ;
//    }

    @GetMapping(value="/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("Status: OK");
    }
    @GetMapping("/event/{eventid}")
    public ResponseEntity<String> joinQueue(@RequestParam("eventid") String message){
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("added to queue");
    }
}


