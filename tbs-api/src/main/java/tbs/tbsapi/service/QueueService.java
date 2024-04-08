package tbs.tbsapi.service;

import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;

public interface  QueueService {
    QueueResponse addtoQueue(QueueRequest queueRequest);

    Queue getQueueByQueueRequest(QueueRequest queueRequest);
    QueueResponse updateQueueStatus(Queue inputqueue);
}
