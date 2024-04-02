package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tbs.tbsapi.vo.request.QueueRequest;

import java.util.ArrayList;

@Log4j2
@Service
public class QueueService {
    public static ArrayList<QueueRequest> arrayListQueue = new ArrayList<>();

    /**
     Returns and deletes the first ticket in the QueueRequest.
     */
    public QueueRequest deleteTicket()
    {
        return arrayListQueue.remove(0);
    }

    /**
     Returns(doesn't delete) the first ticket in the QueueRequest.
     */
    public QueueRequest getFirstTicket()
    {
        return  arrayListQueue.get(0);
    }
}
