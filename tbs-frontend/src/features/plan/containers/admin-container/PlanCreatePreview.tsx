import React, { useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SeatchartJS, { Options, SeatInfo } from "seatchart";
import styles from "./PlanCreatePreview.module.scss";
import Seatchart from "../../../seating-plan/components/Seatchart";
import SeatingPlan from "../../../seating-plan/components/SeatingPlan";
import { addPlanApi, editPlanApi } from "../../plan.api";
import {
  EditSectionSeatReq,
  IAddPlanRequest,
  IEditPlanRequest,
  SectionSeatReq,
} from "../../../../interfaces/seating-plan-interface";
import AlertPopUp from "../../../../common/alert-popup/AlertPopUp";

const PlanCreatePreview: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const planDetails = location.state?.planDetails;
  console.log("plandetails firstonload", planDetails);
  const ops = location.state?.ops;
  const [seatList, setSeatList] = useState<SeatInfo[]>([]);
  const [addPlanSuccess, setAddPlanSuccess] = useState<boolean>(false);
  const [addPlanFailure, setAddPlanFailure] = useState<boolean>(false);

  const navigateBack = () => {
    navigate("/admin/plan/list");
  };

  const editPlan = async () => {
    setAddPlanSuccess(false);
    setAddPlanFailure(false);

    console.log(planDetails.sectionSeats);
    console.log("planDetails", planDetails);

    let lastRowIndex = -1;
    const updatedSectionSeats = planDetails.sectionSeats.map((seat: any) => {
      const noRow = seat.sectionRow;
      const sectionRow = parseInt(seat.sectionRow, 10);
      const startRow = lastRowIndex + 1;
      lastRowIndex = startRow + sectionRow - 1;
      return { ...seat, noRow, sectionRow: String(lastRowIndex) };
    });

    console.log("updatedSectionSeats", updatedSectionSeats);
    console.log("seatlist", seatList);

    let sectionSeatReq: EditSectionSeatReq[] = [];
    for (let category of updatedSectionSeats) {
      // filter out seat names
      const sectionSeats = seatList
        .filter((seat) => seat.type === category.sectionDesc)
        .map((seat) => {
          console.log("updatedSectionseats", updatedSectionSeats);
          return {
            seatId: updatedSectionSeats
              ?.filter((section: any) => section?.sectionDesc === seat.type)[0]
              ?.seatResponses?.filter((s: any) => s?.seatName === seat.label)[0]
              ?.seatId,
            seatName: seat.label,
            seatRow: seat.index.row,
            seatCol: seat.index.col,
            seatStatus: seat.state,
          };
        });

      console.log("sectionSeats", sectionSeats);

      const cat = {
        sectionId: category.sectionId,
        totalSeats: category.noRow * planDetails.col,
        sectionDesc: category.sectionDesc,
        sectionRow: category.sectionRow,
        sectionCol: planDetails.col,
        seatPrice: category.seatPrice,
        seats: sectionSeats,
      };

      sectionSeatReq.push(cat);
    }

    console.log(sectionSeatReq);

    const mappedRequest: IEditPlanRequest = {
      planId: planDetails?.plan,
      venueId: planDetails?.venue,
      planName: planDetails?.planName,
      planRow: Number(planDetails?.row),
      planCol: Number(planDetails?.col),
      sectionSeats: sectionSeatReq,
    };

    console.log(mappedRequest);
    const response = await editPlanApi(mappedRequest);
    console.log(response);
    if (response.message === "SUCCESS" && response.statusCode === "200") {
      setAddPlanSuccess(true);

      setTimeout(() => {
        navigate("/admin/plan/list");
      }, 3000);
    } else {
      setAddPlanFailure(true);
    }
  };

  const createPlan = async () => {
    setAddPlanSuccess(false);
    setAddPlanFailure(false);

    let lastRowIndex = -1;
    const updatedSectionSeats = planDetails.sectionSeats.map((seat: any) => {
      const noRow = seat.sectionRow;
      const sectionRow = parseInt(seat.sectionRow, 10);
      const startRow = lastRowIndex + 1;
      lastRowIndex = startRow + sectionRow - 1;
      return { ...seat, noRow, sectionRow: String(lastRowIndex) };
    });

    console.log(planDetails);
    console.log(updatedSectionSeats);

    let sectionSeatReq: SectionSeatReq[] = [];
    for (let category of updatedSectionSeats) {
      // filter out seat names
      console.log("seatList", seatList);
      const sectionSeats = seatList
        .filter((seat) => seat.type === category.sectionDesc)
        .map((seat) => ({
          seatName: seat.label,
          seatRow: seat.index.row,
          seatCol: seat.index.col,
        }));

      console.log("adding section seat ", sectionSeats);

      const cat = {
        totalSeats: category.noRow * planDetails.col,
        sectionDesc: category.sectionDesc,
        sectionRow: category.sectionRow,
        sectionCol: planDetails.col,
        seatPrice: category.seatPrice,
        seats: sectionSeats,
      };

      sectionSeatReq.push(cat);
    }

    const mappedRequest: IAddPlanRequest = {
      venueId: planDetails.venue,
      planName: planDetails.planName,
      planRow: planDetails.row,
      planCol: planDetails.col,
      sectionSeats: sectionSeatReq,
    };

    const response = await addPlanApi(mappedRequest);

    if (response.message === "SUCCESS" && response.statusCode === "200") {
      setAddPlanSuccess(true);

      setTimeout(() => {
        navigate("/admin/plan/list");
      }, 3000);
    } else {
      setAddPlanFailure(true);
    }
  };

  const handleCreate = async () => {
    if (ops === "Edit") {
      editPlan();
    } else if (ops === "Create") {
      createPlan();
    }
  };

  return (
    <div>
      {addPlanSuccess && (
        <AlertPopUp
          type=""
          message={`Seating Plan ${
            ops === "Edit" ? "editted" : "added"
          } successfully`}
        />
      )}
      {addPlanFailure && (
        <AlertPopUp
          type="danger"
          message={`There's been error while trying to ${
            ops === "Edit" ? "edit" : "add"
          } seating plan`}
        />
      )}
      <div className={`row ${styles.planHeader}`}>
        <div className="col-md-12"></div>
        <h2>Preview Seating Plan</h2>
      </div>
      <div className={`${styles.seatingPlan}`}>
        <SeatingPlan
          row={Number(planDetails?.row)}
          col={Number(planDetails?.col)}
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
          onClick={handleCreate}
        >
          Confirm
        </button>
      </div>
    </div>
  );
};

export default PlanCreatePreview;
