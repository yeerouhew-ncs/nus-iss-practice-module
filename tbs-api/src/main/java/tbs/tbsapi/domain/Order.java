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
@Table(name = "`ORDER`")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "ORDER_DT")
    private LocalDateTime orderDateTime;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "SUBJECT_ID")
    private Integer subjectId;

    @Column(name = "EVENT_ID")
    private Integer eventId;
}
