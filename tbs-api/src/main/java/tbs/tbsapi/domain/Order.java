package tbs.tbsapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "TBS_ORDER")
public class Order {
    @Id
    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "ORDER_DT")
    private LocalDateTime orderDateTime;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EVENT_ID")
    private String eventId;
}
