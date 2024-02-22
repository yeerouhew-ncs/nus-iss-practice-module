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
@Table(name = "SEATING_PLAN")
public class SeatingPlan {
    @Id
    @Column(name="PLAN_ID")
    private String planId;

    @Column(name = "PLAN_FILE_NAME")
    private String planFileName;

    @Column(name = "VENUE_ID")
    private String venueId;
}
