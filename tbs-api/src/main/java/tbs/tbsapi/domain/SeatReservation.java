package tbs.tbsapi.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "SEAT_ID")
    private Integer seatId;

    @Column(name = "SECTION_ID")
    private Integer sectionId;
}
