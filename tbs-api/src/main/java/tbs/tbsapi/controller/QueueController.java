package tbs.tbsapi.controller;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.domain.enums.ResultEnum;
import tbs.tbsapi.manager.QueueManager;
import tbs.tbsapi.service.RabbitMQProducer;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("api/queue")
@CrossOrigin(origins = "*")
public class QueueController {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    QueueManager queueManager;

    public QueueController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }


    @GetMapping(value="/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("Status: OK");
    }
    @PostMapping("/event")
    public @ResponseBody QueueResponse joinQueue(@RequestBody QueueRequest request){
        request.setTicketnumber(String.valueOf(UUID.randomUUID().toString()));
        request.setTimestamp(LocalDateTime.now());
        log.info(String.format("request join queue -> %s",request));
//        QueueManager queueManager = new QueueManager();
        QueueResponse response = queueManager.addtoQueue(request);
        log.info(String.format("sent json -> %s",response));
        boolean isSuccess =rabbitMQProducer.sendJsonMessage(request);
        if (isSuccess) {
            return  response;
        }else{
            QueueResponse queueResponse = new QueueResponse();
            queueResponse.setMessage(ResultEnum.FAIL);
            queueResponse.setStatusCode("400");
            return queueResponse;

        }

    }

    @PostMapping("/check-queue")
    public ResponseEntity<?> checkQueue(@RequestBody QueueRequest request){
//        if (request.getTicketnumber() == )
        Queue retrievedQueue = queueManager.getQueueByQueueRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "queueStatus", retrievedQueue.getQueueStatus(),
                "message", "SUCCESS"));
    }


}


