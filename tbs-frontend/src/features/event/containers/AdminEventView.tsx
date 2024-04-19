import React, { useEffect, useState } from "react";
import styles from "./AdminEventView.module.scss";
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
import SeatingPlan from "../../seating-plan/components/SeatingPlan";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import { IGetPlanDetailsRequest } from "../../../interfaces/seating-plan-interface";
import { getPlanDetailsApi } from "../../plan/plan.api";
import { isTwoWeeksLater } from "../../../utils/date-utils";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: Category[];
};

const AdminEventView: React.FC = () => {
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
    if (userInfo?.authorities[0].authority === "ORGANISER")
      navigate("/organiser/event/edit/" + eventId);
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
    console.log("TWO WEEKS LATER", isTwoWeeksLater(event?.eventFromDt));
  }, [eventId]);

  if (!plan) {
    return (
      <div>
        <div className={`${styles.eventHeader}`}>
          <div>
            <h2>Event Details</h2>
          </div>
          {isTwoWeeksLater(event?.eventFromDt) ? (
            <div>
              <button
                type="button"
                className={`btn btn-primary ${styles.primaryBtn}`}
                onClick={redirectEditOnClick}
                // hidden={userInfo?.authorities[0].authority === "MOP"}
              >
                <span>Edit Event</span>
              </button>
            </div>
          ) : (
            <div></div>
          )}
        </div>
        <div>Plan does not exist</div>
      </div>
    );
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
      <div className={`${styles.eventHeader}`}>
        <div>
          <h2>Event Details</h2>
        </div>
        <div>
          <button
            type="button"
            className={`btn btn-primary ${styles.primaryBtn}`}
            onClick={redirectEditOnClick}
            // hidden={userInfo?.authorities[0].authority === "MOP"}
          >
            <span>Edit Event</span>
          </button>
        </div>
      </div>

      
      <div className={`row`}>
        <div className={`col-md-6 ${styles.eventViewCol}`}>
          <div className={`fw-bold`}>Event Name</div>
          <div className={`${styles.eventViewValue}`}>{event?.eventName}</div>
        </div>
        <div className={`col-md-6 ${styles.eventViewCol}`}>
          <div className={`fw-bold`}>Artist Name</div>
          <div className={`${styles.eventViewValue}`}>
            {event?.artistName}
          </div>
        </div>
        <div className={`col-md-6 ${styles.eventViewCol}`}>
          <div className={`fw-bold`}>Event Start Date</div>
          <div className={`${styles.eventViewValue}`}>
            {moment(event?.eventFromDt).format("DD-MMM-YYYY")}
          </div>
        </div>
        <div className={`col-md-6 ${styles.eventViewCol}`}>
          <div className={`fw-bold`}>Event End Date</div>
          <div className={`${styles.eventViewValue}`}>
            {moment(event?.eventToDt).format("DD-MMM-YYYY")}
          </div>
      </div>
    

        <div className={`row pt-4`}>
          <div className={`col-md-12 fw-bold ${styles.eventViewHeader}`}>
            Seating Plan
          </div>

          <div className={`col-md-12 ${styles.planCard}`}>
            <div className={styles.disabledContainer}>
              <SeatingPlan
                row={plan.row}
                col={plan.col}
                sectionSeats={plan.sectionSeats}
                isLegendVisible={true}
              />
            </div>
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

export default AdminEventView;
