import React, { useEffect, useState } from "react";
import Header from "../../../common/header/Header";
import styles from "./Event.module.scss";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getEventListApi } from "../event.api";
import { setegid } from "process";
import { EventResponse } from "../../../interfaces/event-interface";
import EventItem from "../components/EventItem";

const Event = () => {
  const [events, setEvents] = useState<EventResponse[]>([]);

  useEffect(() => {
    const onLoadEvents = async () => {
      const mappingResult = {
        eventId: null,
        eventName: null,
        artistName: null,
        eventFromDt: null,
        eventToDt: null,
        planId: null,
        usesrId: null,
        page: 0,
      };
      const response = await getEventListApi(mappingResult);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setEvents(response.eventList.content);
      }
    };

    onLoadEvents();
  }, []);

  return (
    <div>
      <div className="row">
        <div className="col-md-4">
          <h2>Upcoming Events</h2>
        </div>
        <div className={`col-md-8 ${styles.inputIcon}`}>
          <FontAwesomeIcon icon={faSearch} />

          <input
            className="form-control rounded-0 border-0 border-bottom"
            placeholder="Search"
          />
        </div>
      </div>
      <div className={styles.eventItemList}>
        {events &&
          events.map((eventInfo, index) => <EventItem eventInfo={eventInfo} />)}
      </div>
    </div>
  );
};

export default Event;
