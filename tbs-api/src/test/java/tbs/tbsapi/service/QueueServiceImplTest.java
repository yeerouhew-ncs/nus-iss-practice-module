package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.domain.enums.ResultEnum;
import tbs.tbsapi.repository.QueueRepository;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueServiceImplTest {

    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private QueueServiceImpl queueService;

    @Test
    void addtoQueue_Success() {
        QueueRequest queueRequest = new QueueRequest();
        queueRequest.setEventId(1);
        queueRequest.setSubjectId(1);
        queueRequest.setTicketnumber("12345");
        queueRequest.setTimestamp(LocalDateTime.now());

        when(queueRepository.save(any())).thenReturn(mock(Queue.class));

        QueueResponse response = queueService.addtoQueue(queueRequest);

        assertEquals("12345", response.getTicketnumber());
        assertEquals(ResultEnum.SUCCESS, response.getMessage());
        verify(queueRepository, times(1)).save(any());
    }

    @Test
    void updateQueueStatus_DontExist() {
        Queue inputQueue = new Queue();
        inputQueue.setQueueId(1);

        when(queueRepository.findByQueueId(inputQueue.getQueueId())).thenReturn(null);

        QueueResponse response = queueService.updateQueueStatus(inputQueue);

        assertEquals("200", response.getStatusCode());
        assertEquals(ResultEnum.FAIL, response.getMessage());
        verify(queueRepository, times(1)).findByQueueId(inputQueue.getQueueId());
        verify(queueRepository, never()).updateQueueStatusByQueueId(anyString(), anyInt());
    }

    @Test
    void updateQueueStatus_Success() {
        Queue inputQueue = new Queue();
        inputQueue.setQueueId(1);
        inputQueue.setTicketnumber("12345");

        when(queueRepository.findByQueueId(inputQueue.getQueueId())).thenReturn(mock(Queue.class));
        when(queueRepository.updateQueueStatusByQueueId(anyString(), anyInt())).thenReturn(1);

        QueueResponse response = queueService.updateQueueStatus(inputQueue);

        assertEquals("200", response.getStatusCode());
        assertEquals(ResultEnum.SUCCESS, response.getMessage());
        assertEquals("12345", response.getTicketnumber());
        verify(queueRepository, times(1)).findByQueueId(inputQueue.getQueueId());
        verify(queueRepository, times(1)).updateQueueStatusByQueueId(anyString(), anyInt());
    }
    @Test
    void updateQueueStatus_updateFail() {
        Queue inputQueue = new Queue();
        inputQueue.setQueueId(1);
        inputQueue.setTicketnumber("12345");

        when(queueRepository.findByQueueId(inputQueue.getQueueId())).thenReturn(mock(Queue.class));
        when(queueRepository.updateQueueStatusByQueueId(anyString(), anyInt())).thenReturn(0);

        QueueResponse response = queueService.updateQueueStatus(inputQueue);

        assertEquals("400", response.getStatusCode());
        assertEquals(ResultEnum.FAIL, response.getMessage());
        verify(queueRepository, times(1)).findByQueueId(inputQueue.getQueueId());
        verify(queueRepository, times(1)).updateQueueStatusByQueueId(anyString(), anyInt());
    }
    @Test
    void getQueueByQueueRequest_ShouldReturnQueue() {
        QueueRequest queueRequest = new QueueRequest();
        queueRequest.setEventId(1);
        queueRequest.setSubjectId(1);
        queueRequest.setTicketnumber("12345");

        Queue expectedQueue = new Queue();
        expectedQueue.setEventId(1);
        expectedQueue.setSubjectId(1);
        expectedQueue.setTicketnumber("12345");

        when(queueRepository.findByIds(1, 1, "12345")).thenReturn(expectedQueue);

        Queue resultQueue = queueService.getQueueByQueueRequest(queueRequest);

        assertEquals(expectedQueue, resultQueue);
        verify(queueRepository, times(1)).findByIds(1, 1, "12345");
    }

}
