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
@Table(name = "SEATING_PLAN")
public class SeatingPlan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="PLAN_ID")
    private Integer planId;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "VENUE_ID")
    private Integer venueId;

    @Column(name = "PLAN_ROW")
    private Integer planRow;

    @Column(name = "PLAN_COL")
    private Integer planCol;
}
