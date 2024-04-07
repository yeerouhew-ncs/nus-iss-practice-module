import { useEffect, useState } from "react";
import styles from "./PlanEdit.module.scss";
import { useNavigate, useParams } from "react-router-dom";
import { Controller, useForm } from "react-hook-form";
import { ErrorBar } from "../../../../common/error-bar/ErrorBar";
import AddCategoryModal from "../../components/AddCategoryModal";
import AlertPopUp from "../../../../common/alert-popup/AlertPopUp";
import {
  IGetPlanDetailsRequest,
  PlanDetails,
} from "../../../../interfaces/seating-plan-interface";
import { getPlanDetailsApi, getVenueListApi } from "../../plan.api";
import { Form } from "react-bootstrap";
import { VenueResponse } from "../../../../interfaces/venue-interface";
import EditCategoryModal from "../../components/EditCategoryModal";
import { Category } from "./PlanCreate";

const PlanEdit: React.FC = () => {
  const params = useParams();
  const navigate = useNavigate();

  const planId = params?.planId;

  const [categoryList, setCategoryList] = useState<Category[]>([]);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
  const [isEditModalVisible, setIsEditModalVisible] = useState<boolean>(false);
  const [disabledAdd, setDisabledAdd] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [venueList, setVenueList] = useState<VenueResponse[]>();
  const [selectedCategory, setSelectedCategory] = useState<Category>();
  const [selectedCategoryIndex, setSelectedCategoryIndex] = useState<number>(0);
  const [planDetails, setPlanDetails] = useState<PlanDetails>();
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

  const navigateBack = () => {
    navigate("/admin/plan/list", { replace: true });
  };

  const handleEditPlan = (data: any) => {
    console.log("plandetails: handle edit", planDetails);
    console.log("categoryList", categoryList);
    navigate("/admin/plan/create/preview", {
      state: {
        planDetails: {
          planName: data?.planName,
          row: data?.row,
          col: data?.col,
          venue: data?.venue,
          sectionSeats: categoryList,
          plan: planDetails?.planId,
          // ...planDetails
        },
        ops: "Edit",
      },
      replace: true,
    });
  };

  useEffect(() => {
    const getPlanDetails = async () => {
      const mappedRequest: IGetPlanDetailsRequest = {
        planId: planId,
        venueId: undefined,
      };
      try {
        const response = await getPlanDetailsApi(mappedRequest);
        if (response.message === "SUCCESS" && response.statusCode === "200") {
          setPlanDetails(response.seatingPlanDetails);

          setValue("planName", response.seatingPlanDetails.planName);
          setValue("row", response.seatingPlanDetails.planRow.toString());
          setValue("col", response.seatingPlanDetails.planCol.toString());
          setValue("venue", response.seatingPlanDetails.venueId);

          const categoryList: Category[] = [];
          console.log(
            "response.seatingPlanDetails.sectionSeatResponses",
            response.seatingPlanDetails.sectionSeatResponses
          );

          // TODO: FIX THIS
          // let currentRow = 0;
          // const catList: Category[] =
          //   response.seatingPlanDetails.sectionSeatResponses.map(
          //     (item, index) => {
          //       let groupSize = response.seatingPlanDetails.sectionSeatResponses
          //         .slice(0, index + 1)
          //         .reduce((acc, curr) => {
          //           return acc + (curr.sectionRow === item.sectionRow ? 1 : 0);
          //         }, 0);

          //       return {
          //         // ...item,
          //         sectionId: Number(item.sectionId),
          //         sectionDesc: item.seatSectionDescription,
          //         sectionRow: currentRow + groupSize,
          //         seatPrice: item.seatPrice,
          //       };
          //     }
          //   );

          // let currentRow = 1; // Start row numbering from 2
          // let prevSectionRow = 0; // Initialize previous sectionRow

          // const catList: Category[] =
          //   response.seatingPlanDetails.sectionSeatResponses.map(
          //     (item, index) => {
          //       let row;
          //       if (index === 0) {
          //         // First item, calculate row based on currentRow and sectionRow
          //         row = currentRow + item.sectionRow - 1;
          //       } else {
          //         // Calculate the difference in sectionRow from the previous item
          //         let sectionRowDiff = item.sectionRow - prevSectionRow;
          //         row = currentRow + sectionRowDiff - 1;
          //       }
          //       currentRow = row + 1; // Update currentRow for the next iteration
          //       prevSectionRow = item.sectionRow; // Update prevSectionRow for the next iteration

          //       return {
          //         sectionId: Number(item.sectionId),
          //         sectionDesc: item.seatSectionDescription,
          //         sectionRow: row,
          //         seatPrice: item.seatPrice,
          //       };
          //     }
          //   );

          let currentRow = 1; // Start row numbering from 2
          let prevSectionRow = 0; // Initialize previous sectionRow
          let rowOffset = 0; // Initialize row offset to adjust for cumulative rows taken by previous section rows

          const catList: Category[] =
            response.seatingPlanDetails.sectionSeatResponses.map(
              (item, index) => {
                let row;
                if (index === 0) {
                  // First item, calculate row based on currentRow and sectionRow
                  row = currentRow + item.sectionRow - 1;
                } else {
                  // Calculate the difference in sectionRow from the previous item
                  let sectionRowDiff = item.sectionRow - prevSectionRow;
                  row = currentRow + sectionRowDiff + rowOffset - 1;
                }
                prevSectionRow = item.sectionRow; // Update prevSectionRow for the next iteration

                // Calculate the cumulative rows taken by previous section rows
                let groupSize = response.seatingPlanDetails.sectionSeatResponses
                  .slice(0, index)
                  .reduce((acc, curr) => {
                    return acc + (curr.sectionRow === item.sectionRow ? 1 : 0);
                  }, 0);
                rowOffset += groupSize;

                currentRow = row + 1; // Update currentRow for the next iteration

                return {
                  sectionId: Number(item.sectionId),
                  sectionDesc: item.seatSectionDescription,
                  sectionRow: row,
                  seatPrice: item.seatPrice,
                };
              }
            );

          // let prevSectionRow = 0;
          // let rowStartIndex = 1;

          // const result = response.seatingPlanDetails.sectionSeatResponses
          //   .map((item, index) => {
          //     const row = item.sectionRow - prevSectionRow;
          //     prevSectionRow = item.sectionRow;

          //     const newRow = Array.from({ length: row - 1 }, (_, index) => ({
          //       sectionRow: rowStartIndex + index + 1, // Adjust the row numbering to start from 2
          //       sectionId: Number(item.sectionId),
          //       sectionDesc: item.seatSectionDescription,
          //       seatPrice: item.seatPrice,
          //     }));

          //     const newItem = {
          //       sectionId: Number(item.sectionId),
          //       sectionDesc: item.seatSectionDescription,
          //       sectionRow: rowStartIndex + index + 1,
          //       seatPrice: item.seatPrice,
          //     };
          //     rowStartIndex += row - 1;

          //     return newRow;
          //   })
          //   .flat();

          // console.log("TRANSFORMED RESULT", result);

          // for (let sectionSeat of response.seatingPlanDetails
          //   .sectionSeatResponses) {
          //   const cat: Category = {
          //     sectionId: Number(sectionSeat.sectionId),
          //     sectionDesc: sectionSeat.seatSectionDescription,
          //     // TODO: update sectionRow to be accurate
          //     sectionRow: sectionSeat.sectionRow,
          //     seatPrice: sectionSeat.seatPrice,
          //   };
          //   categoryList.push(cat);
          // }
          setCategoryList(catList);
          console.log("categoryList", catList);
        }
      } catch (error) {
        // TODO: error handling
        console.log(error);
      }
    };

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

    getPlanDetails();
  }, []);

  useEffect(() => {
    console.log(formState.isValid);
    if (categoryList && categoryList?.length >= 10) {
      setDisabledAdd(true);
    } else {
      setDisabledAdd(false);
    }
  }, [categoryList]);

  const editCategory = (index: number) => {
    console.log("CategoryList", categoryList);
    const cat: Category = categoryList[index];
    console.log(cat);
    setIsEditModalVisible(true);

    setSelectedCategory(cat);
    setSelectedCategoryIndex(index);
  };

  return (
    <div>
      {errorMsg && <AlertPopUp type="danger" message={errorMsg} />}
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
              {!categoryList && (
                <div style={{ fontSize: "14px" }}>
                  You have not added category
                </div>
              )}
              {categoryList &&
                categoryList.length > 0 &&
                categoryList.map((cat, index) => (
                  <div key={index} className={`${styles.categoryItem}`}>
                    <div>
                      {index + 1}. {cat.sectionDesc} | ${cat.seatPrice} |{" "}
                      {cat.sectionRow} rows
                    </div>
                    <button
                      type="button"
                      className={`btn btn-sm ${styles.primaryBtn} ${styles.btnMarginRight}`}
                      onClick={() => editCategory(index)}
                    >
                      Edit
                    </button>
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
              onClick={handleSubmit(handleEditPlan)}
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

      <EditCategoryModal
        isEditModalVisible={isEditModalVisible}
        setIsEditModalVisible={setIsEditModalVisible}
        categoryList={categoryList}
        setCategoryList={setCategoryList}
        totalRows={Number(getValues("row"))}
        setErrorMsg={setErrorMsg}
        selectedCategory={selectedCategory}
        selectedCategoryIndex={selectedCategoryIndex}
      />
      {/* <Seatchart ref={seatchartRef} options={options} /> */}

      {/* <div className="form-group row">
                <form>
                <div className="col-md-12 mb-3">
                    <label className="form-label">Layout ID</label>
                    <input type="text" id="planId" className="form-control" value="3" disabled></input> 
                </div>
                <div className="col-md-12 mb-3">
                    <label className="form-label">Layout Filename</label>
                    <input type="text" id="planFileName" className="form-control"></input>
                </div>
                <div className="col-md-12 mb-3">
                    <label className="form-label">Venue</label>
                    <select id="venueId" className="form-select">
                        <option value="0">Esplanade Concert Hall</option>
                        <option value="1">Indoor Sports Hall</option>
                        <option value="2">National Stadium</option>
                        <option value="3">The Star Theatre</option>
                    </select>
                </div>
                <div className={`row`}>
                    <div className={`col-md-12 ${styles.createBtnGroup}`}>
                    <button
                        type="button"
                        // onClick={navigateBack}
                        className={`btn btn-primary ${styles.primaryBtn}`}
                    >
                        Cancel
                    </button>
                    <button
                        type="button"
                        onClick={createAddPlanDto}
                        className={`btn btn-primary ${styles.primaryBtn}`}
                    >
                        Submit
                    </button>
                    </div>
                </div>
                </form>
            </div> */}
    </div>
  );
};

export default PlanEdit;
