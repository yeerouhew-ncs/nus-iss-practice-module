package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.domain.Order;
import tbs.tbsapi.domain.SeatReservation;
import tbs.tbsapi.domain.SeatSection;
import tbs.tbsapi.dto.AddOrderDto;
import tbs.tbsapi.factory.OrderFactory;
import tbs.tbsapi.factory.SeatReservationFactory;
import tbs.tbsapi.repository.*;
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

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private SeatReservationFactory seatReservationFactory;

    @Transactional
    public AddOrderResponse addOrder(AddOrderDto addOrderDto) {
        Order newOrder = orderFactory.addOrder(addOrderDto);
        log.info("ORDER {} ", newOrder);

        //GET PLAN_ID
        Event event = eventRepository.findByEventId(addOrderDto.getEventId());

        //GET SECTION_ID_LIST
        log.info("EVENT: {}", event);
        log.info("PLAN ID: {}", event.getPlanId());

        List<Integer> sectionIdList = sectionSeatRepository.findSectionIdsByPlanId(event.getPlanId());

        //UPDATE SEAT_STATUS TO 'reserved'
        List<SeatSection> seatSectionIdList = seatRepository.findBySeatNameSectionId(addOrderDto.getSeatNames(), sectionIdList);
        log.info("SECTION ID LIST: {}", sectionIdList);
        log.info("SEAT SECTION ID LIST: {}", seatSectionIdList);
        for (SeatSection ss: seatSectionIdList) {
            log.info("Section: {}, Seat: {}", ss.getSectionId(), ss.getSeatId());
        }
        seatRepository.updateSeatsReserved(addOrderDto.getSeatNames(), sectionIdList);

        Order saveOrder = orderRepository.save(newOrder);

        //ADD TO SEAT_RESERVATION TABLE
        for (SeatSection seatSectionId : seatSectionIdList) {
            SeatReservation newReservation = seatReservationFactory.addSeatReservation(saveOrder.getOrderId(), seatSectionId);
            seatReservationRepository.save(newReservation);
        }

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
