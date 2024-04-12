package tbs.tbsapi.factory;

import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.SeatReservation;
import tbs.tbsapi.domain.SeatSection;

@Component
public class SeatReservationFactory {
    public SeatReservation addSeatReservation(Integer orderId, SeatSection seatSection) {
            return SeatReservation.builder()
                    .orderId(orderId)
                    .seatId(seatSection.getSeatId())
                    .sectionId(seatSection.getSectionId())
                    .build();
        }
}
