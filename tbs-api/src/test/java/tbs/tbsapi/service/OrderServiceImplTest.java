package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderFactory orderFactory;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SectionSeatRepository sectionSeatRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SeatReservationRepository seatReservationRepository;

    @Mock
    private SeatReservationFactory seatReservationFactory;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void addOrder_Success() {
        // Arrange
        AddOrderDto addOrderDto = new AddOrderDto();
        addOrderDto.setEventId(1);
        addOrderDto.setSeatNames(List.of("A1", "A2"));
        when(orderFactory.addOrder(addOrderDto)).thenReturn(mock(Order.class));
        when(eventRepository.findByEventId(addOrderDto.getEventId())).thenReturn(mock(Event.class));
        when(sectionSeatRepository.findSectionIdsByPlanId(anyInt())).thenReturn(List.of(1, 2));
        when(seatRepository.findBySeatNameSectionId(anyList(), anyList())).thenReturn(List.of(mock(SeatSection.class), mock(SeatSection.class)));

        when(orderRepository.save(any())).thenReturn(mock(Order.class));
        when(seatReservationFactory.addSeatReservation(anyInt(), any())).thenReturn(mock(SeatReservation.class));

        // Act
        AddOrderResponse response = orderService.addOrder(addOrderDto);

        assertEquals("200", response.getStatusCode());
        assertEquals("SUCCESS", response.getMessage());
    }

}
