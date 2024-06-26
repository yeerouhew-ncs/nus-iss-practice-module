import React, { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import styles from "./EventCreate.module.scss";
import { IGetPlanDetailsRequest } from "../../../interfaces/seating-plan-interface";
import { getPlanDetailsApi } from "../../plan/plan.api";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import SeatingPlan, {
  SectionSeatType,
} from "../../seating-plan/components/SeatingPlan";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: SectionSeatType[];
};

interface PlanViewModalProps {
  isModalVisible: boolean;
  setIsModalVisible: any;
  planId: string;
  selectedPlanLayout: SeatingPlanType | undefined;
  setSelectedPlanLayout: React.Dispatch<
    React.SetStateAction<SeatingPlanType | undefined>
  >;
}

const PlanViewModal: React.FC<PlanViewModalProps> = ({
  isModalVisible,
  setIsModalVisible,
  planId,
  selectedPlanLayout,
  setSelectedPlanLayout,
}) => {
  const closeModal = () => {
    setSelectedPlanLayout(undefined);
    setIsModalVisible(false);
  };

  return (
    <>
      {selectedPlanLayout && (
        <Modal
          show={isModalVisible}
          onHide={closeModal}
          keyboard={false}
          backdrop="static"
          //   dialogClassName={styles.viewPlanModal}
          contentClassName={styles.viewPlanModal}
        >
          <Modal.Header closeButton={true}>
            <Modal.Title>View Layout</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className={styles.disabledContainer}>
              {/* <SeatingPlanOne planId={planId} legendVisible={false} /> */}
              <SeatingPlan
                row={selectedPlanLayout.row}
                col={selectedPlanLayout.col}
                sectionSeats={selectedPlanLayout.sectionSeats}
                isLegendVisible={true}
              />
            </div>
          </Modal.Body>
        </Modal>
      )}
    </>
  );
};

export default PlanViewModal;
