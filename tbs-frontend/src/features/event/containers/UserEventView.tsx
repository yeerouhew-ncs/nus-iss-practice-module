import React, { useEffect, useState } from "react";
import styles from "./UserEventView.module.scss";
import { useNavigate, useParams } from "react-router-dom";
import { getEventDetailsApi } from "../event.api";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import { EventResponse } from "../../../interfaces/event-interface";
import moment from "moment";
import { useAuthContext } from "../../../context/AuthContext";
import SeatingPlan from "../../seating-plan/components/SeatingPlan";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import {
  GetSeatResponse,
  IGetPlanDetailsRequest,
} from "../../../interfaces/seating-plan-interface";
import { getPlanDetailsApi } from "../../plan/plan.api";
import OrderSeatingPlan from "../../seating-plan/components/OrderSeatingPlan";
import Breadcrumb from "../../../common/breadcrumb/Breadcrumb";
import { SeatInfo } from "seatchart";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: SectionSeatType[];
};

type SectionSeatType = {
  sectionId?: number | null;
  sectionDesc: string;
  sectionRow: number;
  seatPrice: number;
  seatResponses: GetSeatResponse[];
};

export type OrderType = {
  orderSeatInfo: SeatInfo[] | undefined;
  orderTotalPrice: number | undefined;
  event: EventResponse | undefined;
};

const UserEventView: React.FC = () => {
  const param = useParams();
  const eventId = param?.eventId;

  const [errors, setErrors] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [warning, setWarning] = useState<boolean>(false);
  const [event, setEvent] = useState<EventResponse>();
  const [plan, setPlan] = useState<SeatingPlanType>();
  // const [order, setOrder] = useState<OrderType>();

  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);

  const navigate = useNavigate();
  const { userInfo } = useAuthContext();

  const navigateBack = () => {
    navigate("/user/event/list", { replace: true });
  };
  const navigateQueue = () => {
    navigate("/user/event/queue/"+eventId, { replace: true });
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
        console.log("USER EVENT VIEW DETAILS ", response);
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
      <Breadcrumb activeStep={0} />
      <br />
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
            <div>
              <OrderSeatingPlan
                row={plan.row}
                col={plan.col}
                sectionSeats={plan.sectionSeats}
                isLegendVisible={true}
                // setOrder={setOrder}
                event={event}
              />
              <button
                  type="button"
                  className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
                  onClick={navigateQueue}
              ><span>Enter Queue</span>
              </button>
            </div>
          </div>
        </div>

        <br />
        <br />
        {/* <div className={styles.viewBtnGroup}>
          <div
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={navigateBack}
          >
            Back
          </div>
          <div
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={navigateToOrder}
          >
            Proceed to Payment
          </div>
        </div> */}
      </div>
    </div>
  );
};

export default UserEventView;
