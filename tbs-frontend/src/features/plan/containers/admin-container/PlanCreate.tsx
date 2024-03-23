import { useEffect, useRef, useState } from "react";
import styles from "./PlanCreate.module.scss";
import { useNavigate } from "react-router-dom";
import SeatchartJS, { Options } from "seatchart";
import { Controller, useForm } from "react-hook-form";
import { ErrorBar } from "../../../../common/error-bar/ErrorBar";
import AddCategoryModal from "../../components/AddCategoryModal";
import AlertPopUp from "../../../../common/alert-popup/AlertPopUp";
import { Form } from "react-bootstrap";
import { getVenueListApi } from "../../plan.api";
import { VenueResponse } from "../../../../interfaces/venue-interface";

export type Category = {
  sectionId?: number | null;
  sectionDesc: string;
  sectionRow: number;
  seatPrice: number;
};

const PlanCreate = () => {
  const navigate = useNavigate();
  const [categoryList, setCategoryList] = useState<Category[]>([]);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
  const [disabledAdd, setDisabledAdd] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [venueList, setVenueList] = useState<VenueResponse[]>();

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
      planName: "",
      row: "",
      col: "",
      venue: "",
    },
  });

  // const createAddPlanDto = () => {
  //   // DUMMY FUNCTION - TO BE REPLACED WITH ACTUAL FUNCTION
  //   var formData = new FormData();

  //   formData.append(
  //     "planId",
  //     (document.getElementById("planId") as HTMLInputElement).value
  //   );
  //   formData.append(
  //     "planFileName",
  //     (document.getElementById("planFileName") as HTMLInputElement).value
  //   );
  //   formData.append(
  //     "venueId",
  //     (document.getElementById("venueId") as HTMLInputElement).value
  //   );

  //   var jsonStr = JSON.stringify(Object.fromEntries(formData));
  //   console.log("JSON STRING", jsonStr);
  //   alert("Mock create plan: check console log for JSON!");
  // };

  const navigateBack = () => {
    navigate("/admin/plan/list", { replace: true });
  };

  const handleCreatePlan = (data: any) => {
    navigate("/admin/plan/create/preview", {
      state: {
        planDetails: {
          planName: data?.planName,
          row: data?.row,
          col: data?.col,
          venue: data?.venue,
          sectionSeats: categoryList,
        },
        ops: "Create",
      },
      replace: true,
    });
  };

  useEffect(() => {
    if (categoryList && categoryList?.length >= 10) {
      setDisabledAdd(true);
    } else {
      setDisabledAdd(false);
    }
  }, [categoryList]);

  useEffect(() => {
    //call retrieve venue api
    const getVenues = async () => {
      try {
        const response = await getVenueListApi();
        if (response?.statusCode === "200" && response?.message === "SUCCESS") {
          setVenueList(response.venueList);
        }
      } catch (error) {
        console.log(error);
      }
    };

    getVenues();
  }, []);

  return (
    <div>
      {errorMsg !== "" && <AlertPopUp type="danger" message={errorMsg} />}
      <div className={`row ${styles.planHeader}`}>
        <div className="col-md-12">
          <h2>Add New Seating Plan</h2>
        </div>
      </div>
      <div>
        <div className={styles.formCreatePlan}>
          <div className={`row`}>
            <div className={`col-12 ${styles.rowMargin}`}>
              <label
                htmlFor="planName"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.row ? "error-red" : ""
                }`}
              >
                Name of Seating Plan
              </label>
              <Controller
                name="planName"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter the plan name",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="planName"
                    name="planName"
                    // onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    //   rowOnChange(e, field)
                    // }
                  />
                )}
              />
              {formState.errors?.planName && (
                <ErrorBar errorMsg={formState.errors.planName?.message} />
              )}
            </div>
          </div>
          <div className={`row`}>
            <div className={`col-12 ${styles.rowMargin}`}>
              <label
                htmlFor="row"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.row ? "error-red" : ""
                }`}
              >
                Total Number of Rows
              </label>
              <Controller
                name="row"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter the row",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="row"
                    name="row"
                    // onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    //   rowOnChange(e, field)
                    // }
                  />
                )}
              />
              {formState.errors?.row && (
                <ErrorBar errorMsg={formState.errors.row?.message} />
              )}
            </div>
          </div>
          <div className={`row`}>
            <div className={`col-12 ${styles.rowMargin}`}>
              <label
                htmlFor="col"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.col ? "error-red" : ""
                }`}
              >
                Total Number of Columns
              </label>
              <Controller
                name="col"
                control={control}
                rules={{
                  validate: {},
                  required: "Please enter the column",
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    className="form-control"
                    type="text"
                    id="col"
                    name="col"
                  />
                )}
              />
              {formState.errors?.col && (
                <ErrorBar errorMsg={formState.errors.col?.message} />
              )}
            </div>
          </div>
          <div className={`row`}>
            <div className={`col-12 ${styles.rowMargin}`}>
              <label
                htmlFor="col"
                className={`form-preview-field-label ${styles.requiredField} ${
                  formState.errors?.venue ? "error-red" : ""
                }`}
              >
                Venue
              </label>
              <Controller
                name="venue"
                control={control}
                rules={{
                  required: "Please select the venue",
                }}
                render={({ field }) => (
                  <Form.Select
                    {...field}
                    id="venue"
                    className="rounded-0"
                    onBlur={() => {}}
                  >
                    <option hidden value="">
                      Select
                    </option>
                    {venueList &&
                      venueList.length > 0 &&
                      venueList.map((venue, index) => (
                        <option key={index} value={venue.venueId}>
                          {venue.venueName}
                        </option>
                      ))}
                  </Form.Select>
                )}
              />
              {formState.errors?.venue && (
                <ErrorBar errorMsg={formState.errors.venue?.message} />
              )}
            </div>
          </div>

          <div className={`${styles.categoryContainer}`}>
            <div className={`${styles.categoryHeaderGroup}`}>
              <div>
                <h6 className={`${styles.categoryHeader}`}>Categories</h6>
                <span style={{ fontSize: "12px", color: "red" }}>
                  Note: Please add category in ascending order, for example:
                  Category 1 to Category 6
                </span>
              </div>

              <button
                type="button"
                className={`btn btn-sm ${styles.primaryBtn} ${styles.btnMarginRight}`}
                onClick={() => {
                  setIsModalVisible(true);
                  setErrorMsg("");
                }}
                disabled={disabledAdd || !formState.isValid}
              >
                Add
              </button>
            </div>
            <hr />
            <div className={`${styles.categoryResult}`}>
              {categoryList.length <= 0 && (
                <div style={{ fontSize: "14px" }}>
                  You have not added category
                </div>
              )}
              {categoryList &&
                categoryList.length > 0 &&
                categoryList.map((cat, index) => (
                  <div key={index}>
                    <div>
                      {index + 1}. {cat.sectionDesc} | ${cat.seatPrice} |{" "}
                      {cat.sectionRow} rows
                    </div>
                  </div>
                ))}
            </div>
          </div>

          <div className={`${styles.btnGroup}`}>
            <button
              className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
              type="button"
              onClick={navigateBack}
            >
              Cancel
            </button>

            <button
              className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
              type="button"
              onClick={handleSubmit(handleCreatePlan)}
            >
              Preview
            </button>
          </div>
        </div>
      </div>

      <AddCategoryModal
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        categoryList={categoryList}
        setCategoryList={setCategoryList}
        totalRows={Number(getValues("row"))}
        setErrorMsg={setErrorMsg}
      />
    </div>
  );
};

export default PlanCreate;
