package tbs.tbsapi.vo.request;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVenueRequest {
    @Null
    private String venueName;
    @Null
    private String address;
}
