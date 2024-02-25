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
@Table(name = "VENUE")
public class Venue {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "VENUE_ID")
    private Integer venueId;

    @Column(name = "VENUE_NAME")
    private String venueName;

    @Column(name = "ADDRESS")
    private String address;
}
