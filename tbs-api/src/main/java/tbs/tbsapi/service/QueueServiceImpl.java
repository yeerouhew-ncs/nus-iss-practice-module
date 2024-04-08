package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.domain.enums.QueueStatus;
import tbs.tbsapi.domain.enums.ResultEnum;
import tbs.tbsapi.repository.QueueRepository;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;

@Log4j2
@Service
public class QueueServiceImpl implements QueueService{
    @Autowired
    private QueueRepository queueRepository;

    public QueueResponse addtoQueue(QueueRequest queueRequest){
        Queue newQueue = Queue.builder()
                .queueStatus(String.valueOf(QueueStatus.WAITING))
                .eventId(queueRequest.getEventId())
                .subjectId(queueRequest.getSubjectId())
                .queueDateTime(queueRequest.getTimestamp())
                .ticketnumber(queueRequest.getTicketnumber())
                .build();
        Queue savedQueue = queueRepository.save(newQueue);
        QueueResponse queueResponse = new QueueResponse();
        queueResponse.setTicketnumber(queueRequest.getTicketnumber());
        queueResponse.setMessage(ResultEnum.SUCCESS);
        return queueResponse;
    }

    public Queue getQueueByQueueRequest(QueueRequest queueRequest){
        return queueRepository.findByIds(queueRequest.getEventId(),
                queueRequest.getSubjectId(),queueRequest.getTicketnumber());
    }

    @Transactional
    public QueueResponse updateQueueStatus(Queue inputqueue){
        QueueResponse response = new QueueResponse();
        if(queueRepository.findByQueueId(inputqueue.getQueueId()) == null){
            response.setStatusCode("200");
            response.setMessage(ResultEnum.FAIL);
            return  response;
        }
        Integer isUpdated = queueRepository.updateQueueStatusByQueueId(
                String.valueOf(QueueStatus.PROCESSING),
                inputqueue.getQueueId());
        if(isUpdated == 1) {
            response.setStatusCode("200");
            response.setMessage(ResultEnum.SUCCESS);
            response.setTicketnumber(inputqueue.getTicketnumber());
        }else{
            response.setStatusCode("400");
            response.setMessage(ResultEnum.FAIL);
        }
        return  response;

    }
}
