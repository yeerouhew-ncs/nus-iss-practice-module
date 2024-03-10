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
@Table(name = "SECTION_SEAT")
public class SectionSeat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SECTION_ID")
    private Integer sectionId;

    @Column(name = "PLAN_ID")
    private Integer planId;

    @Column(name = "TOTAL_SEATS")
    private Integer totalSeats;

    @Column(name = "NO_SEATS_LEFT")
    private Integer noSeatsLeft;

    @Column(name = "SEAT_PRICE")
    private Double seatPrice;

    @Column(name = "SECTION_DESC")
    private String seatSectionDescription;

    @Column(name = "SECTION_ROW")
    private Integer seatRow;

    @Column(name = "SECTION_COL")
    private Integer seatCol;
}
