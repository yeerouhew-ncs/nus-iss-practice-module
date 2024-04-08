package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.service.EventService;
import tbs.tbsapi.service.QueueService;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;


@Log4j2
@Service
public class QueueManager {
    /**
     Generates the ticket and adds it to the queue.
     */
    @Autowired
    private QueueService queueService;

    public QueueResponse addtoQueue(QueueRequest queueRequest)
    {

        log.info("START: ADD QUEUE " + queueRequest.toString());


        return queueService.addtoQueue(queueRequest);
    }
    public Queue getQueueByQueueRequest (QueueRequest queueRequest)
    {

        log.info("START: GET QUEUE" + queueRequest.toString());


        return queueService.getQueueByQueueRequest(queueRequest);
    }
}
