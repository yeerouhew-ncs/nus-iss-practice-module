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
@Table(name = "SECTION_SEAT")
public class SectionSeat {
    @Id
    @Column(name = "SECTION_ID")
    private String sectionId;

    @Column(name = "PLAN_ID")
    private String planId;

    @Column(name = "SEAT_ID")
    private String seatId;

    @Column(name = "TOTAL_SEATS")
    private Integer totalSeats;

    @Column(name = "NO_SEATS_LEFT")
    private Integer noSeatsLeft;

    @Column(name = "SEAT_PRICE")
    private Double seatPrice;


}
