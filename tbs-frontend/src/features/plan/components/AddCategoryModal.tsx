import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import styles from "./AddCategoryModal.module.scss";
import { Controller, useForm } from "react-hook-form";
import { ErrorBar } from "../../../common/error-bar/ErrorBar";
import { Category } from "../containers/admin-container/PlanCreate";

type AddCategoryModalProps = {
  isModalVisible: boolean;
  setIsModalVisible: Dispatch<SetStateAction<boolean>>;
  categoryList: Category[] | undefined;
  setCategoryList: Dispatch<SetStateAction<Category[]>>;
  totalRows: number;
  setErrorMsg: Dispatch<SetStateAction<string>>;
};

const AddCategoryModal: React.FC<AddCategoryModalProps> = ({
  isModalVisible,
  setIsModalVisible,
  categoryList,
  setCategoryList,
  totalRows,
  setErrorMsg,
}) => {
  const {
    control,
    formState,
    handleSubmit,
    setValue,
    getValues,
    setError,
    clearErrors,
    register,
    reset,
  } = useForm({
    mode: "onChange",
    defaultValues: {
      sectionDesc: "",
      sectionRow: "",
      seatPrice: "",
    },
  });

  const closeModal = () => {
    setErrorMsg("");
    clearErrors();
    reset();
    setIsModalVisible(false);
  };

  const validateRow = (data: any) => {
    const list = [...(categoryList || []), data];

    let totalAddedRows = list?.reduce(
      (total, seat) => total + Number(seat.sectionRow),
      0
    );

    if (!totalAddedRows) {
      totalAddedRows = 0;
    }
    console.log("totalRows", totalRows);
    console.log("totalAddedRows", totalAddedRows);
    console.log(
      "totalAddedRows && totalAddedRows >= totalRows",
      totalAddedRows && totalAddedRows >= totalRows
    );

    if (totalAddedRows && totalAddedRows > totalRows) {
      return true;
    }

    return false;
  };

  const handleAdd = (data: any) => {
    setErrorMsg("");
    const isAddedRowExceeds = validateRow(data);

    if (!isAddedRowExceeds) {
      const category: Category = {
        sectionId: null,
        sectionDesc: data?.sectionDesc,
        sectionRow: Number(data?.sectionRow),
        seatPrice: Number(data?.seatPrice),
      };
      setCategoryList((prev) => [...(prev || []), category]);
    } else {
      setErrorMsg(
        "Number of rows exceed the total number of rows for seating plan"
      );
    }

    reset();
    setIsModalVisible(false);
  };

  return (
    <div>
      <Modal
        show={isModalVisible}
        onHide={closeModal}
        keyboard={false}
        backdrop="static"
        contentClassName={styles.addCategoryModal}
      >
        <Modal.Header closeButton={true}>
          <Modal.Title>Add Category</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className={`${styles.addCategoryContainer} row`}>
            <div className={`col-12`}>
              <label
                htmlFor="sectionDesc"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.sectionDesc ? "error-red" : ""
                }`}
              >
                Description
              </label>
              <Controller
                name="sectionDesc"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter description",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="sectionDesc"
                    name="sectionDesc"
                    // onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    //   rowOnChange(e, field)
                    // }
                  />
                )}
              />
              {formState.errors?.sectionDesc && (
                <ErrorBar errorMsg={formState.errors.sectionDesc?.message} />
              )}
            </div>
            <div className={`col-12`}>
              <label
                htmlFor="sectionRow"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.sectionRow ? "error-red" : ""
                }`}
              >
                Number of Rows
              </label>
              <Controller
                name="sectionRow"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter number of rows",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="sectionRow"
                    name="sectionRow"
                    // onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    //   rowOnChange(e, field)
                    // }
                  />
                )}
              />
              {formState.errors?.sectionRow && (
                <ErrorBar errorMsg={formState.errors.sectionRow?.message} />
              )}
            </div>
            <div className={`col-12`}>
              <label
                htmlFor="seatPrice"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.seatPrice ? "error-red" : ""
                }`}
              >
                Seat Price
              </label>
              <Controller
                name="seatPrice"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter seat price",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="seatPrice"
                    name="seatPrice"
                    // onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    //   rowOnChange(e, field)
                    // }
                  />
                )}
              />
              {formState.errors?.seatPrice && (
                <ErrorBar errorMsg={formState.errors.seatPrice?.message} />
              )}
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <button
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            type="button"
            onClick={closeModal}
          >
            Cancel
          </button>

          <button
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            type="button"
            onClick={handleSubmit(handleAdd)}
          >
            Add
          </button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default AddCategoryModal;
