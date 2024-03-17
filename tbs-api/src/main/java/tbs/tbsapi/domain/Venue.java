package tbs.tbsapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

//    public static void initializeMultiple(EntityManager entityManager) {
//        Venue venue = new Venue();
//        venue.setVenueId(1);
//        venue.setVenueName("The Star Performing Arts Centre");
//        venue.setAddress("1 Vista Exchange Green, #04-01, Singapore 138617");
//        entityManager.persist(venue);
//
//        Venue venue2 = new Venue();
//        venue2.setVenueId(1);
//        venue2.setVenueName("Sands Theatre");
//        venue2.setAddress("10 Bayfront Ave, Singapore 018956");
//        entityManager.persist(venue2);
//    }
}
