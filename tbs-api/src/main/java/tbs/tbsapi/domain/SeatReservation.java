package tbs.tbsapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SEAT_RESERVATION")
public class SeatReservation {
    @Id
    @Column(name = "RESERVATION_ID")
    private String reservationId;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "SEAT_ID")
    private String seatId;

    @Column(name = "SECTION_ID")
    private String sectionId;
}
