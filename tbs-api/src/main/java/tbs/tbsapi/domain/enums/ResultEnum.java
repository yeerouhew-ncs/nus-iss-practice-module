package tbs.tbsapi.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResultEnum {

    @JsonProperty("SUCCESS")
    SUCCESS,
    @JsonProperty("FAIL")
    FAIL;

}
