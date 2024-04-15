package tbs.tbsapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tbs.tbsapi.controller.QueueController;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.manager.QueueManager;
import tbs.tbsapi.service.QueueService;
import tbs.tbsapi.service.RabbitMQProducer;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;
import tbs.tbsapi.domain.enums.ResultEnum;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QueueControllerTest {

    @InjectMocks
    private QueueController queueController;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @Mock
    private QueueManager queueManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJoinQueue_Success() {
        QueueRequest request = new QueueRequest();
        QueueManager queueManager = new QueueManager();
        QueueService service = mock(QueueService.class);
        queueController.queueManager = queueManager;
        queueManager.queueService = service;
        QueueResponse mockResponse = new QueueResponse();
        mockResponse.setMessage(ResultEnum.SUCCESS);
        mockResponse.setStatusCode("200");
        when(queueManager.addtoQueue(request)).thenReturn(mockResponse);

        when(rabbitMQProducer.sendJsonMessage(any(QueueRequest.class))).thenReturn(true);

        QueueResponse response = queueController.joinQueue(request);

        // Assertions
        assertEquals(ResultEnum.SUCCESS, response.getMessage());
        assertEquals("200", response.getStatusCode());
    }

    @Test
    void testJoinQueue_Failure() {
        QueueRequest request = new QueueRequest();
        queueController.queueManager = queueManager;
        when(rabbitMQProducer.sendJsonMessage(any(QueueRequest.class))).thenReturn(false);

        QueueResponse response = queueController.joinQueue(request);

        assertEquals(ResultEnum.FAIL, response.getMessage());
        assertEquals("400", response.getStatusCode());
    }
    @Test
    void testCheckQueue_Success() {
        QueueRequest request = new QueueRequest();

        Queue queue = new Queue();
        queue.setQueueStatus("ACTIVE");
        queueController.queueManager = queueManager;
        when(queueManager.getQueueByQueueRequest(any(QueueRequest.class))).thenReturn(queue);

        ResponseEntity<?> responseEntity = queueController.checkQueue(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map<String, String> responseBody = (Map<String, String>) responseEntity.getBody();
        assertEquals("ACTIVE", responseBody.get("queueStatus"));
    }
}
