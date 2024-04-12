package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.domain.Order;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.factory.OrderFactory;
import tbs.tbsapi.repository.EventRepository;
import tbs.tbsapi.repository.OrderRepository;
import tbs.tbsapi.repository.SeatRepository;
import tbs.tbsapi.repository.SectionSeatRepository;
import tbs.tbsapi.vo.response.AddOrderResponse;

import java.util.List;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderFactory orderFactory;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SectionSeatRepository sectionSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    public AddOrderResponse addOrder(AddOrderDto addOrderDto) {
        Order newOrder = orderFactory.addOrder(addOrderDto);
        log.info("ORDER {} ", newOrder);

        //GET PLAN_ID
        Event event = eventRepository.findByEventId(addOrderDto.getEventId());

        //GET SECTION_ID_LIST
        List<Integer> sectionIdList = sectionSeatRepository.findSectionIdsByPlanId(event.getPlanId());

        //UPDATE SEAT_STATUS TO 'reserved'
        seatRepository.updateSeatsReserved(addOrderDto.getSeatNames(), sectionIdList);

        Order saveOrder = orderRepository.save(newOrder);

        AddOrderResponse orderResponse = new AddOrderResponse();
        orderResponse.setStatusCode("200");
        orderResponse.setMessage("SUCCESS");
        orderResponse.setOrderId(saveOrder.getOrderId());
        orderResponse.setEventId(saveOrder.getEventId());
        orderResponse.setOrderDateTime(saveOrder.getOrderDateTime());
        orderResponse.setOrderStatus(saveOrder.getOrderStatus());
        orderResponse.setSubjectId(saveOrder.getSubjectId());
        orderResponse.setTotalPrice(saveOrder.getTotalPrice());

        return orderResponse;
    }
}
