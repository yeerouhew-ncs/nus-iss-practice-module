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
@Table(name = "SEAT")
public class Seat {
    @Id
    @Column(name = "SEAT_ID")
    private String seatId;

    @Column(name = "SEAT_STATUS")
    private String seatStatus;

    @Column(name = "SECTION_ID")
    private Integer sectionId;
}
