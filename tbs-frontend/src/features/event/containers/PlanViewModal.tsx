import React from "react";
import Modal from "react-bootstrap/Modal";
import SeatingPlanOne from "../../seating-plan/containers/SeatingPlanOne";
import styles from "./EventCreate.module.scss";

const PlanViewModal = ({
  isModalVisible,
  setIsModalVisible,
  //   closeModal,
  planId,
}: {
  isModalVisible: boolean;
  setIsModalVisible: any;
  //   closeModal: any;
  planId: string;
}) => {
  const closeModal = () => {
    setIsModalVisible(false);
  };

  return (
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
          <SeatingPlanOne planId={planId} legendVisible={false} />
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default PlanViewModal;
