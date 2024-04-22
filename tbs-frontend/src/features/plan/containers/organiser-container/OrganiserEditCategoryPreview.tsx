import React, { useState } from "react";

import styles from "./OrganiserEditCategoryPreview.module.scss";
import { useLocation, useNavigate } from "react-router-dom";
import SeatingPlan from "../../../seating-plan/components/SeatingPlan";
import { SeatInfo } from "seatchart";
import { editPlanCategoryApi } from "../../plan.api";
import { EditSectionSeatReq } from "../../../../interfaces/seating-plan-interface";
import AlertPopUp from "../../../../common/alert-popup/AlertPopUp";

const OrganiserEditCategoryPreview = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [seatList, setSeatList] = useState<SeatInfo[]>([]);
  const [addPlanSuccess, setAddPlanSuccess] = useState<boolean>(false);
  const [addPlanFailure, setAddPlanFailure] = useState<boolean>(false);

  const planDetails = location.state?.planDetails;

  const navigateBack = () => {
    navigate("/organiser/plan/list");
  };

  const handleEdit = async () => {
    let lastRowIndex = -1;
    const updatedSectionSeats = planDetails.sectionSeats.map((seat: any) => {
      const noRow = seat.sectionRow;
      const sectionRow = parseInt(seat.sectionRow, 10);
      const startRow = lastRowIndex + 1;
      lastRowIndex = startRow + sectionRow - 1;
      return { ...seat, noRow, sectionRow: String(lastRowIndex) };
    });

    console.log("updatedSectionSeats", updatedSectionSeats);
    let sectionSeatReq: EditSectionSeatReq[] = [];
    for (let category of updatedSectionSeats) {
      const sectionSeats = seatList
        .filter((seat) => seat.type === category.sectionDesc)
        .map((seat) => ({
          seatId: updatedSectionSeats
            ?.filter((section: any) => section?.sectionDesc === seat.type)[0]
            ?.seatResponses?.filter((s: any) => s?.seatName === seat.label)[0]
            ?.seatId,
          seatName: seat.label,
          seatRow: seat.index.row,
          seatCol: seat.index.col,
          seatStatus: seat.state,
        }));

      console.log("sectionSeats organiser", sectionSeats);

      const cat = {
        sectionId: category.sectionId,
        totalSeats: category.noRow * planDetails.planData.planCol,
        sectionDesc: category.sectionDesc,
        sectionRow: Number(category.sectionRow),
        sectionCol: planDetails.planData.planCol,
        seatPrice: category.seatPrice,
        seats: sectionSeats,
      };

      sectionSeatReq.push(cat);
    }

    const mappedRequest = {
      planId: planDetails.planData.planId,
      sectionSeats: sectionSeatReq,
    };

    console.log("mappedRequest", mappedRequest);
    const response = await editPlanCategoryApi(mappedRequest);
    if (response.message === "SUCCESS" && response.statusCode === "200") {
      setAddPlanSuccess(true);

      setTimeout(() => {
        navigate("/organiser/plan/list");
      }, 3000);
    } else {
      setAddPlanFailure(true);
    }
  };

  if (!planDetails) {
    navigate("/organiser/plan/list");
  }

  return (
    <div>
      {addPlanSuccess && (
        <AlertPopUp type="" message={`Seat category editted successfully`} />
      )}
      {addPlanFailure && (
        <AlertPopUp
          type="danger"
          message={`There's been error while trying to edit seat category`}
        />
      )}
      <div className={`row ${styles.planHeader}`}>
        <div className="col-md-12"></div>
        <h2>Preview Seating Plan</h2>
      </div>
      <div className={`${styles.seatingPlan}`}>
        <SeatingPlan
          row={Number(planDetails?.planData.planRow)}
          col={Number(planDetails?.planData.planCol)}
          sectionSeats={planDetails?.sectionSeats}
          isLegendVisible={true}
          setSeatList={setSeatList}
        />
      </div>
      <div className={`${styles.btnGroup}`}>
        <button
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          type="button"
          onClick={navigateBack}
        >
          Back
        </button>

        <button
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          type="button"
          onClick={handleEdit}
        >
          Confirm
        </button>
      </div>
    </div>
  );
};

export default OrganiserEditCategoryPreview;
