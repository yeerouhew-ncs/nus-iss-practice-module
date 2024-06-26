import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import styles from "./AddCategoryModal.module.scss";
import { Controller, useForm } from "react-hook-form";
import { ErrorBar } from "../../../common/error-bar/ErrorBar";
import { Category } from "../containers/admin-container/PlanCreate";

type EditCategoryModalProps = {
  isEditModalVisible: boolean;
  setIsEditModalVisible: Dispatch<SetStateAction<boolean>>;
  categoryList: Category[] | undefined;
  setCategoryList: Dispatch<SetStateAction<Category[]>>;
  totalRows: number;
  setErrorMsg: Dispatch<SetStateAction<string>>;
  selectedCategory: Category | undefined;
  selectedCategoryIndex: number;
};

const EditCategoryModal: React.FC<EditCategoryModalProps> = ({
  isEditModalVisible,
  setIsEditModalVisible,
  categoryList,
  setCategoryList,
  totalRows,
  setErrorMsg,
  selectedCategory,
  selectedCategoryIndex,
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
    setIsEditModalVisible(false);
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
    // setErrorMsg("");
    // const isAddedRowExceeds = validateRow(data);

    // if (!isAddedRowExceeds) {
    const category: Category = {
      sectionId: selectedCategory?.sectionId,
      sectionDesc: data?.sectionDesc,
      sectionRow: Number(data?.sectionRow),
      seatPrice: Number(data?.seatPrice),
    };

    // setCategoryList((prev) => [...(prev || []), category]);
    setCategoryList((prev) => {
      const updatedList = [...prev];
      updatedList[selectedCategoryIndex] = category;
      return updatedList;
    });
    // } else {
    //   console.log("ERROR");
    //   setErrorMsg(
    //     "Number of rows exceed the total number of rows for seating plan"
    //   );
    // }

    reset();
    setIsEditModalVisible(false);
  };

  useEffect(() => {
    if (selectedCategory) {
      setValue("sectionDesc", selectedCategory?.sectionDesc);
      setValue("sectionRow", selectedCategory?.sectionRow.toString());
      setValue("seatPrice", selectedCategory?.seatPrice.toString());
    }
  }, [selectedCategory]);

  return (
    <div>
      <Modal
        show={isEditModalVisible}
        onHide={closeModal}
        keyboard={false}
        backdrop="static"
        contentClassName={styles.addCategoryModal}
      >
        <Modal.Header closeButton={true}>
          <Modal.Title>Edit Category</Modal.Title>
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
                    disabled
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
            Submit
          </button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default EditCategoryModal;
