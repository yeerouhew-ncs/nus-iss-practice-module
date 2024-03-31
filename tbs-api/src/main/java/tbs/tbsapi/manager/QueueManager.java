package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.service.QueueService;
import tbs.tbsapi.vo.request.QueueRequest;


@Log4j2
@Service
public class QueueManager {
    /**
     Generates the ticket and adds it to the queue.
     */
    public void generateTicket(QueueRequest queueRequest)
    {
        int ticketNumber = 0;
        for(Object item : QueueService.arrayListQueue)
        {
            ticketNumber = (int) item;
        }
        ticketNumber++;
        queueRequest.setTicketnumber(ticketNumber);
        QueueService.arrayListQueue.add(queueRequest);
    }
}
