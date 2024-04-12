package tbs.tbsapi.factory;

import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.Order;
import tbs.tbsapi.dto.AddOrderDto;

@Component
public class OrderFactory {
    public Order addOrder(AddOrderDto addOrderDto) {
        return Order.builder()
                .eventId(addOrderDto.getEventId())
                .orderDateTime(addOrderDto.getOrderDateTime())
                .orderStatus(addOrderDto.getOrderStatus())
                .subjectId(addOrderDto.getSubjectId())
                .totalPrice(addOrderDto.getTotalPrice())
                .build();
    }
}
