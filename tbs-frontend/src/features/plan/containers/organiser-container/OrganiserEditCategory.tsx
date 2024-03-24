import React, { useEffect, useState } from "react";
import styles from "./OrganiserEditCategory.module.scss";
import { getPlanDetailsApi } from "../../plan.api";
import {
  IGetPlanDetailsRequest,
  PlanList,
} from "../../../../interfaces/seating-plan-interface";
import { useNavigate, useParams } from "react-router-dom";
import { Category } from "../admin-container/PlanCreate";
import EditCategoryModal from "../../components/EditCategoryModal";
import AlertPopUp from "../../../../common/alert-popup/AlertPopUp";
import AddCategoryModal from "../../components/AddCategoryModal";

const OrganiserEditCategory = () => {
  const params = useParams();
  const navigate = useNavigate();
  const planId = params?.planId;

  const [selectedPlan, setSelectedPlan] = useState<PlanList>();
  const [categoryList, setCategoryList] = useState<Category[]>([]);
  const [isEditModalVisible, setIsEditModalVisible] = useState<boolean>(false);
  const [selectedCategory, setSelectedCategory] = useState<Category>();
  const [selectedCategoryIndex, setSelectedCategoryIndex] = useState<number>(0);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [disabledAdd, setDisabledAdd] = useState<boolean>(false);
  // const [plan, setPlan] = useState<SeatingPlanType>();

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
          setSelectedPlan(response.seatingPlanDetails);
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
          //   setPlan(seatingPlan);

          let currentRow = 0;
          const catList: Category[] =
            response.seatingPlanDetails.sectionSeatResponses.map(
              (item, index) => {
                let groupSize = response.seatingPlanDetails.sectionSeatResponses
                  .slice(0, index + 1)
                  .reduce((acc, curr) => {
                    return acc + (curr.sectionRow === item.sectionRow ? 1 : 0);
                  }, 0);

                return {
                  // ...item,
                  sectionId: Number(item.sectionId),
                  sectionDesc: item.seatSectionDescription,
                  sectionRow: currentRow + groupSize,
                  seatPrice: item.seatPrice,
                };
              }
            );

          setCategoryList(catList);
        }
      } catch (error) {
        // TODO: error handling
        console.log(error);
      }
    };

    getPlanDetails();
  }, []);

  const editCategory = (index: number) => {
    console.log("CategoryList", categoryList);
    const cat: Category = categoryList[index];
    console.log(cat);
    setIsEditModalVisible(true);

    setSelectedCategory(cat);
    setSelectedCategoryIndex(index);
  };

  const navigateBack = () => {
    navigate("/organiser/plan/list", { replace: true });
  };

  const handleEditPlan = () => {
    navigate("/organiser/plan/edit-category/preview", {
      state: {
        planDetails: {
          planData: selectedPlan,
          sectionSeats: categoryList,
        },
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

  return (
    <div>
      {errorMsg && <AlertPopUp type="danger" message={errorMsg} />}
      <div>
        <div className={` ${styles.planViewHeader}`}>Add / Edit Category</div>
      </div>
      <div className={styles.planViewContainer}>
        <div className={`row`}>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>Plan Name</div>
            <div className={`${styles.planViewValue}`}>
              {selectedPlan?.planName}
            </div>
          </div>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>Venue Name</div>
            <div className={`${styles.planViewValue}`}>
              {selectedPlan?.venueName}
            </div>
          </div>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>
              Total Number of Rows
            </div>
            <div className={`${styles.planViewValue}`}>
              {selectedPlan?.planRow}
            </div>
          </div>
          <div className={`col-md-12 ${styles.planViewCol}`}>
            <div className={`${styles.planViewLabel}`}>
              Total Number of Columns
            </div>
            <div className={`${styles.planViewValue}`}>
              {selectedPlan?.planCol}
            </div>
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
              disabled={disabledAdd}
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
            onClick={handleEditPlan}
          >
            Preview
          </button>
        </div>
      </div>
      <AddCategoryModal
        isModalVisible={isModalVisible}
        setIsModalVisible={setIsModalVisible}
        categoryList={categoryList}
        setCategoryList={setCategoryList}
        totalRows={Number(selectedPlan?.planRow)}
        setErrorMsg={setErrorMsg}
      />

      <EditCategoryModal
        isEditModalVisible={isEditModalVisible}
        setIsEditModalVisible={setIsEditModalVisible}
        categoryList={categoryList}
        setCategoryList={setCategoryList}
        totalRows={Number(selectedPlan?.planRow)}
        setErrorMsg={setErrorMsg}
        selectedCategory={selectedCategory}
        selectedCategoryIndex={selectedCategoryIndex}
      />
    </div>
  );
};

export default OrganiserEditCategory;
