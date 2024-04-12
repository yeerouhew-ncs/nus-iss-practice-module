package tbs.tbsapi.domain;

import jakarta.persistence.*;
import lombok.*;
import tbs.tbsapi.domain.enums.SeatStatus;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SEAT")
public class Seat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SEAT_ID")
    private Integer seatId;

    @Column(name = "SEAT_NAME")
    private String seatName;

    @Column(name = "SEAT_STATUS")
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Column(name = "SEAT_ROW")
    private Integer seatRow;

    @Column(name = "SEAT_COL")
    private Integer seatCol;

    @Column(name = "SECTION_ID")
    private Integer sectionId;
}
