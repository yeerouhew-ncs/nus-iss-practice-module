package tbs.tbsapi.manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.service.QueueService;
import tbs.tbsapi.vo.request.QueueRequest;
import tbs.tbsapi.vo.response.QueueResponse;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class QueueManagerTest {

    @Mock
    private QueueService queueService;

    @InjectMocks
    private QueueManager queueManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToQueue() {
        // Mocking
        QueueRequest queueRequest = new QueueRequest();
        QueueResponse expectedResponse = new QueueResponse();
        when(queueService.addtoQueue(queueRequest)).thenReturn(expectedResponse);

        // Method call
        QueueResponse actualResponse = queueManager.addtoQueue(queueRequest);

        // Verification
        verify(queueService, times(1)).addtoQueue(queueRequest);
        assertSame(expectedResponse, actualResponse);
    }

    @Test
    public void testGetQueueByQueueRequest() {
        // Mocking
        QueueRequest queueRequest = new QueueRequest();
        Queue expectedQueue = new Queue();
        when(queueService.getQueueByQueueRequest(queueRequest)).thenReturn(expectedQueue);

        // Method call
        Queue actualQueue = queueManager.getQueueByQueueRequest(queueRequest);

        // Verification
        verify(queueService, times(1)).getQueueByQueueRequest(queueRequest);
        assertSame(expectedQueue, actualQueue);
    }
}
