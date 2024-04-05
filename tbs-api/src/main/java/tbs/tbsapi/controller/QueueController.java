package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.service.RabbitMQProducer;
import tbs.tbsapi.vo.request.QueueRequest;

import java.util.UUID;

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
    @PostMapping("/event")
    public ResponseEntity<String> joinQueue(@RequestBody QueueRequest request){
        request.setTicketnumber(String.valueOf(UUID.randomUUID().toString()));
        rabbitMQProducer.sendJsonMessage(request);
        return ResponseEntity.ok(String.format("added to queue %s",request.getEventId()));
    }

    @PostMapping("/check-queue")
    public ResponseEntity<String> checkQueue(@RequestBody QueueRequest request){

        return ResponseEntity.ok("");
    }

}


