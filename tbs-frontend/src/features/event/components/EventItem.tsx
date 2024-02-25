import React from "react";
import styles from "./EventItem.module.scss";
import { EventResponse } from "../../../interfaces/event-interface";
import moment from "moment";

const EventItem = ({ eventInfo }: { eventInfo: EventResponse }) => {
  return (
    <div>
      <div className="row">
        <div className="col-md-2">
          {eventInfo.eventFromDt !== null
            ? moment(eventInfo.eventFromDt).format("YYYY-MM-DD")
            : null}{" "}
          -{" "}
          {eventInfo.eventToDt !== null
            ? moment(eventInfo.eventToDt).format("YYYY-MM-DD")
            : null}
        </div>
        <div className="col-md-2">{eventInfo.eventName}</div>
        <div className="col-md-2">{eventInfo.artistName}</div>
      </div>
    </div>
  );
};

export default EventItem;
