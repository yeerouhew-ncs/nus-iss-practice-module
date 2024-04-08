package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tbs.tbsapi.vo.request.QueueRequest;

import java.text.MessageFormat;

@Log4j2
@Service
public class RabbitMQProducer {
    @Value("${spring.rabbitmq.queue.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.queue.routing.key}")
    private String routingkey;


    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(QueueRequest queueRequest){
        log.info(String.format("JSON msg sent -? %s",queueRequest.toString()));
        rabbitTemplate.convertAndSend(exchange,routingkey,queueRequest);

    }
}
