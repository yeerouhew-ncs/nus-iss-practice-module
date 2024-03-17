import React, { useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SeatchartJS, { Options, SeatInfo } from "seatchart";
import styles from "./PlanCreatePreview.module.scss";
import Seatchart from "../../seating-plan/components/Seatchart";
import SeatingPlan from "../../seating-plan/components/SeatingPlan";
import { addPlanApi, editPlanApi } from "../plan.api";
import {
  IAddPlanRequest,
  IEditPlanRequest,
  SectionSeatReq,
} from "../../../interfaces/seating-plan-interface";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";

const PlanCreatePreview = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const planDetails = location.state?.planDetails;
  const ops = location.state?.ops;
  const [seatList, setSeatList] = useState<SeatInfo[]>([]);
  const [addPlanSuccess, setAddPlanSuccess] = useState<boolean>(false);
  const [addPlanFailure, setAddPlanFailure] = useState<boolean>(false);

  const navigateBack = () => {
    navigate("/event/list");
  };

  const editPlan = async () => {
    setAddPlanSuccess(false);
    setAddPlanFailure(false);

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

    // TODO: get seat from db

    // let sectionSeatReq: SectionSeatReq[] = [];
    // for (let category of updatedSectionSeats) {
    //   // filter out seat names
    //   const sectionSeats = seatList
    //     .filter((seat) => seat.type === category.sectionDesc)
    //     .map((seat) => seat.label)
    //     .map((seatName) => ({ seatName }));

    //   console.log(sectionSeats);

    //   const cat = {
    //     sectionId: category.sectionId,
    //     totalSeats: category.noRow * planDetails.col,
    //     sectionDesc: category.sectionDesc,
    //     sectionRow: category.sectionRow,
    //     sectionCol: planDetails.col,
    //     seatPrice: category.seatPrice,
    //     seats: sectionSeats,
    //   };

    //   sectionSeatReq.push(cat);
    // }

    // console.log(sectionSeatReq);

    // const mappedRequest: IEditPlanRequest = {
    //   planId: planDetails?.planId,
    //   venueId: planDetails?.venueId,
    //   planName: planDetails?.planName,
    //   planRow: planDetails?.planRow,
    //   planCol: planDetails?.planCol,
    //   sectionSeats: sectionSeatReq,
    // };

    // const response = await editPlanApi(mappedRequest);
    // console.log(response);
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
      const sectionSeats = seatList
        .filter((seat) => seat.type === category.sectionDesc)
        .map((seat) => seat.label)
        .map((seatName) => ({ seatName }));

      console.log(sectionSeats);

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

    console.log(seatList);
    const response = await addPlanApi(mappedRequest);

    if (response.message === "SUCCESS" && response.statusCode === "200") {
      setAddPlanSuccess(true);

      setTimeout(() => {
        navigate("/plan/list");
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
        <AlertPopUp type="" message="Seating Plan added successfully" />
      )}
      {addPlanFailure && (
        <AlertPopUp
          type="danger"
          message="There's been error while trying to add seating plan"
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
          Create
        </button>
      </div>
    </div>
  );
};

export default PlanCreatePreview;
