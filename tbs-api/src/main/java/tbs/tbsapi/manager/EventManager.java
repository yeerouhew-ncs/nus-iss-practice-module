package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class EventManager {
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("statusCode", "200"));
    }
}
