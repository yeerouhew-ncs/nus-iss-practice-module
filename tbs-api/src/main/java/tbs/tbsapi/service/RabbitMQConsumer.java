//package tbs.tbsapi.service;
//
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//import tbs.tbsapi.vo.request.QueueRequest;
//
//@Log4j2
//@Service
//
//public class RabbitMQConsumer {
//
//
//    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
//    public void consume(QueueRequest queueRequest){
//        log.info(String.format("Received queueRequest -> %s",queueRequest));
//
//    }
//
//}
