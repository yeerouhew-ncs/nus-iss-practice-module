package tbs.tbsapi.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbs.tbsapi.domain.enums.EventType;

import java.util.EnumMap;

@Log4j2
@Component
public class EventsFactory {
    @Autowired
    public Concert concert;

    @Autowired
    public SportsEvents sportsEvents;

    private EnumMap<EventType, Events> eventsMap;

    public EventsFactory() {
        this.eventsMap = new EnumMap<>(EventType.class);
        this.eventsMap.put(EventType.CONCERT, new Concert());
        this.eventsMap.put(EventType.SPORTS, new SportsEvents());
    }

    public Events getEvent(EventType eventType) {
        log.info("EVENTSMAP {} ", eventsMap);
        log.info("eventType {} ", eventType);
        return this.eventsMap.get(eventType);
    }
}
