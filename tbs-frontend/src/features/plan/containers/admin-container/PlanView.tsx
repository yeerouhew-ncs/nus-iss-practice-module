import React, { useEffect, useState } from "react";
import styles from "./PlanView.module.scss";
import { getPlanDetailsApi } from "../../plan.api";
import { useNavigate, useParams } from "react-router-dom";
import {
  IGetPlanDetailsRequest,
  PlanDetails,
} from "../../../../interfaces/seating-plan-interface";
import SeatingPlan from "../../../seating-plan/components/SeatingPlan";
import { Category } from "./PlanCreate";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: Category[];
};

const PlanView: React.FC = () => {
  const params = useParams();
  const navigate = useNavigate();
  const planId = params?.planId;

  const [plan, setPlan] = useState<SeatingPlanType>();

  useEffect(() => {
    const getPlanDetails = async () => {
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

    getPlanDetails();
  }, []);

  const navigateBack = () => {
    navigate("/admin/plan/list", { replace: true });
  };

  // TODO: error handling
  if (!plan) {
    return <div>Plan does not exist</div>;
  }

  return (
    <div>
      <div>
        <div className={` ${styles.planViewHeader}`}>Seating Plan Details</div>
      </div>
      <div className={styles.planViewContainer}>
        <div className={`row`}>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>Plan Name</div>
            <div className={`${styles.planViewValue}`}>{plan?.planName}</div>
          </div>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>Venue Name</div>
            <div className={`${styles.planViewValue}`}>{plan?.venueName}</div>
          </div>
        </div>

        <div className={`row`}>
          <div className={`col-md-12 ${styles.planViewHeader}`}>
            Seating Plan
          </div>

          <div className={`col-md-12 ${styles.planCard}`}>
            <div className={styles.disabledContainer}>
              <SeatingPlan
                row={plan.row}
                col={plan.col}
                sectionSeats={plan?.sectionSeats}
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

export default PlanView;
