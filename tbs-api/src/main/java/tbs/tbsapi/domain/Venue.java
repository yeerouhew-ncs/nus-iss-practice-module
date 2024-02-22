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
@Table(name = "VENUE")
public class Venue {
    @Id
    @Column(name = "VENUE_ID")
    private String venueId;

    @Column(name = "VENUE_NAME")
    private String venueName;

    @Column(name = "ADDRESS")
    private String address;
}
