import styles from "./EventItem.module.scss";
import { EventResponse } from "../../../interfaces/event-interface";
import moment from "moment";

interface EventItemProps {
  eventInfo: EventResponse;
}

const EventItem: React.FC<EventItemProps> = ({ eventInfo }) => {
  const displayDay = (day: string): string => {
    const dayInNum = parseInt(day);
    if (dayInNum < 10) {
      return "0" + dayInNum.toString();
    }
    return dayInNum.toString();
  };

  const displayMonth = (month: number): string => {
    month += 1;
    switch (month) {
      case 1:
        return "JAN";
      case 2:
        return "FEB";
      case 3:
        return "MAR";
      case 4:
        return "APR";
      case 5:
        return "MAY";
      case 6:
        return "JUN";
      case 7:
        return "JUL";
      case 8:
        return "AUG";
      case 9:
        return "SEP";
      case 10:
        return "OCT";
      case 11:
        return "NOV";
      case 12:
        return "DEC";
    }
    return "";
  };

  return (
    <div className={styles.eventItemContainer}>
      <div className={`row ${styles.eventItemRow}`}>
        <div className={`col-md-3 ${styles.eventDateTimeCol}`}>
          <div className={styles.eventDateTime}>
            <div className={styles.eventDateTimeDay}>
              {displayDay(
                moment(eventInfo.eventFromDt, "YYYY-MM-DD").format("D")
              )}
            </div>
            <div className={styles.eventDateTimeMonth}>
              {displayMonth(moment(eventInfo.eventFromDt).month())}
            </div>
          </div>
          <div className={styles.eventDateTime}>-</div>
          <div className={styles.eventDateTime}>
            <div className={styles.eventDateTimeDay}>
              {displayDay(
                moment(eventInfo.eventToDt, "YYYY-MM-DD").format("D")
              )}
            </div>
            <div className={styles.eventDateTimeMonth}>
              {displayMonth(moment(eventInfo.eventToDt).month())}
            </div>
          </div>
        </div>
        <div className="col-md-5">{eventInfo.eventName}</div>
        <div className="col-md-4">{eventInfo.artistName}</div>
      </div>
    </div>
  );
};

export default EventItem;
