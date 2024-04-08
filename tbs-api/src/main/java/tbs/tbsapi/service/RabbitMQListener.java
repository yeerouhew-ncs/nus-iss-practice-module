package tbs.tbsapi.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Queue;

import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;



@Log4j2
@Component
public class RabbitMQListener  {

    @Autowired
    private QueueService queueService;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    public void consume(QueueRequest qr) throws Exception  {

        try {

            Queue calledQueue = queueService.getQueueByQueueRequest(qr);
            QueueResponse response = queueService.updateQueueStatus(calledQueue);
            log.info(String.format("Called queueRequest -> %s",qr));
            log.info(String.format("Called updateQueueStatus -> %s",response));

        }catch (Exception e){
            log.error(String.format("error caught %s",e.getMessage()));
        }


    }

}
