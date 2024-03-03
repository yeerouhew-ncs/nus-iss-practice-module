import React, { useEffect, useState } from "react";
import styles from "./EventView.module.scss";
import { useNavigate, useParams } from "react-router-dom";
import { getEventDetailsApi } from "../event.api";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import { EventResponse } from "../../../interfaces/event-interface";
import moment from "moment";

const EventView = () => {
  const param = useParams();
  const eventId = param?.eventId;

  const [errors, setErrors] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [warning, setWarning] = useState<boolean>(false);
  const [event, setEvent] = useState<EventResponse>();

  const navigate = useNavigate();

  const navigateBack = () => {
    navigate("/event", { replace: true });
  };

  const getEventDetails = async () => {
    const mappingRequest = {
      eventId: eventId,
      subjectId: undefined,
    };
    try {
      const response = await getEventDetailsApi(mappingRequest);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setEvent(response.eventDetails);
      } else if (
        response.statusCode === "200" &&
        response.message === "NO MATCHING EVENT"
      ) {
        setWarning(true);
        setErrorMsg("There is no matching event");
      }
    } catch (err) {
      setErrors(true);
      setErrorMsg(err as string);
    }
  };

  useEffect(() => {
    getEventDetails();
  }, [eventId]);

  return (
    <div>
      {warning && (
        <AlertPopUp type="warning" message={errorMsg} duration={5000} />
      )}
      {errors && (
        <AlertPopUp type="danger" message={errorMsg} duration={5000} />
      )}
      <div>
        <div className={` ${styles.eventViewHeader}`}>Event Details</div>
      </div>
      <div className={styles.eventViewContainer}>
        <div className={`row`}>
          <div className={`col-md-12 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event Name</div>
            <div className={`${styles.eventViewValue}`}>{event?.eventName}</div>
          </div>
          <div className={`col-md-12 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Artist Name</div>
            <div className={`${styles.eventViewValue}`}>
              {event?.artistName}
            </div>
          </div>
          <div className={`col-md-12 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event Start Date</div>
            <div className={`${styles.eventViewValue}`}>
              {moment(event?.eventFromDt).format("DD-MMM-YYYY")}
            </div>
          </div>
          <div className={`col-md-12 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event End Date</div>
            <div className={`${styles.eventViewValue}`}>
              {moment(event?.eventToDt).format("DD-MMM-YYYY")}
            </div>
          </div>
        </div>

        <div className={`row`}>
          <div className={`col-md-12 ${styles.eventViewHeader}`}>
            Seating Plan
          </div>

          <div className={`col-md-12 ${styles.planCard}`}>
            <label className="layersMenu">
              <img
                className={`${styles.planThumbnail}`}
                src={require("../../../images/mock_layout.png")}
                alt="layout2"
              />
            </label>
          </div>
        </div>

        <br />
        <br />
        <div className={styles.viewBtnGroup}>
          <div
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={navigateBack}
          >
            Back
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventView;
