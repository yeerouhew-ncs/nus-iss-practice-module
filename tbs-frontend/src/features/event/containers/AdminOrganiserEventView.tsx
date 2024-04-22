//
import React, { useEffect, useState } from "react";
import styles from "./AdminOrganiserEventView.module.scss";
import { useNavigate, useParams } from "react-router-dom";
import { getEventDetailsApi } from "../event.api";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import {
  EventDetailsResponse,
  EventResponse,
} from "../../../interfaces/event-interface";
import moment from "moment";
import PlanViewModal from "./PlanViewModal";
import { useAuthContext } from "../../../context/AuthContext";
import SeatingPlan, {
  SectionSeatType,
} from "../../seating-plan/components/SeatingPlan";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import {
  GetSeatResponse,
  IGetPlanDetailsRequest,
} from "../../../interfaces/seating-plan-interface";
import { getPlanDetailsApi } from "../../plan/plan.api";
import { isTwoWeeksLater } from "../../../utils/date-utils";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: SectionSeatType[];
};

const AdminOrganiserEventView: React.FC = () => {
  const param = useParams();
  const eventId = param?.eventId;

  const [errors, setErrors] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [warning, setWarning] = useState<boolean>(false);
  const [event, setEvent] = useState<EventDetailsResponse>();
  const [plan, setPlan] = useState<SeatingPlanType>();

  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);

  const navigate = useNavigate();
  const { userInfo } = useAuthContext();

  const navigateBack = () => {
    if (userInfo?.authorities[0].authority === "ADMIN")
      navigate("/admin/event/list", { replace: true });
    else if (userInfo?.authorities[0].authority === "ORGANISER")
      navigate("/organiser/event/list", { replace: true });
  };

  const redirectEditOnClick = () => {
    if (userInfo?.authorities[0].authority === "ORGANISER") {
      navigate("/organiser/event/edit/" + eventId);
    } else if (userInfo?.authorities[0].authority === "ADMIN") {
      navigate("/admin/event/edit/" + eventId);
    }
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
        getPlanDetails(response.eventDetails.planId);
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

  const getPlanDetails = async (planId: string) => {
    const mappedRequest: IGetPlanDetailsRequest = {
      planId: planId,
      venueId: undefined,
    };
    try {
      const response = await getPlanDetailsApi(mappedRequest);
      if (response.message === "SUCCESS" && response.statusCode === "200") {
        console.log(response.seatingPlanDetails);
        const sectionSeat =
          response.seatingPlanDetails.sectionSeatResponses.map((section) => ({
            sectionDesc: section.seatSectionDescription,
            sectionRow: section.sectionRow,
            seatPrice: section.seatPrice,
            seatResponses: section.seatResponses,
          }));
        console.log("sectionSeat", sectionSeat);
        // process section seat row
        const newSectionSeat = sectionSeat.map((item, index, array) => {
          let sectionRow;
          if (index === 0) {
            sectionRow = item.sectionRow + 1;
          } else {
            sectionRow = item.sectionRow - array[index - 1].sectionRow;
          }
          return {
            ...item,
            sectionRow,
          };
        });

        console.log("newSectionSeat", newSectionSeat);

        const seatingPlan = {
          planName: response.seatingPlanDetails.planName,
          venueName: response.seatingPlanDetails.venueName,
          row: response.seatingPlanDetails.planRow,
          col: response.seatingPlanDetails.planCol,
          sectionSeats: newSectionSeat,
        };

        console.log("seatingPlan ", seatingPlan);
        setPlan(seatingPlan);
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  useEffect(() => {
    getEventDetails();
  }, [eventId]);

  if (!plan) {
    return <div>Plan does not exist</div>;
  }

  return (
    <div>
      {warning && <AlertPopUp type="warning" message={errorMsg} />}
      {errors && <AlertPopUp type="danger" message={errorMsg} />}
      {/* <PlanViewModal
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        planId={event ? event.planId : "1"}
      /> */}
      <div>
        <div className={` ${styles.eventViewHeader}`}>Event Details</div>
      </div>
      <div className={styles.eventViewContainer}>
        <div className={`row`}>
          <div className={`col-md-6 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event Name</div>
            <div className={`${styles.eventViewValue}`}>{event?.eventName}</div>
          </div>
          <div className={`col-md-6 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Artist Name</div>
            <div className={`${styles.eventViewValue}`}>
              {event?.artistName}
            </div>
          </div>
          <div className={`col-md-6 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event Start Date</div>
            <div className={`${styles.eventViewValue}`}>
              {moment(event?.eventFromDt).format("DD-MMM-YYYY")}
            </div>
          </div>
          <div className={`col-md-6 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event End Date</div>
            <div className={`${styles.eventViewValue}`}>
              {moment(event?.eventToDt).format("DD-MMM-YYYY")}
            </div>
          </div>
          <div className={`col-md-6 ${styles.eventViewCol}`}>
            <div className={`${styles.eventViewLabel}`}>Event Type</div>
            <div className={`${styles.eventViewValue}`}>{event?.eventType}</div>
          </div>
          <div
            className={`col-md-6 ${styles.eventViewCol}`}
            hidden={
              event?.genre === null ||
              event?.genre === undefined ||
              event.genre === ""
            }
          >
            <div className={`${styles.eventViewLabel}`}>Genre</div>
            <div className={`${styles.eventViewValue}`}>{event?.genre}</div>
          </div>
        </div>

        <div className={`row`}>
          <div className={`col-md-12 ${styles.eventViewHeader}`}>
            Seating Plan
          </div>

          <div className={`col-md-12 ${styles.planCard}`}>
            <div className={styles.disabledContainer}>
              <SeatingPlan
                row={plan.row}
                col={plan.col}
                sectionSeats={plan.sectionSeats}
                isLegendVisible={true}
                isViewEvent={true}
              />
            </div>
          </div>
        </div>

        <br />
        <br />
        <div className={`${styles.viewBtnGroup} d-flex align-items-center`}>
          <div
            className={`btn ${styles.primaryBtn} btn-sm`}
            onClick={navigateBack}
          >
            Back
          </div>
          <div>
            <button
              type="button"
              className={`btn btn-sm ${styles.primaryBtn}`}
              onClick={redirectEditOnClick}
            >
              <span>Edit Event</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminOrganiserEventView;
