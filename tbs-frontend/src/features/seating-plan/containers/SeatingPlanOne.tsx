import React, { useState, useRef, useEffect } from "react";
import styles from "./Seating.module.scss";
import "seatchart/dist/seatchart.min.css";
import SeatchartJS, { Options, SeatIndex } from "seatchart";
import Seatchart from "../components/Seatchart";
import { getSeatingPlanDetailsApi } from "../seating-plan.api";
import { PlanDetails } from "../../../interfaces/seating-plan-interface";

// TODO: REMOVE THIS

interface ICategoryPlan {
  price: number;
  row: number[];
}

const SeatingPlanOne = ({
  planId,
  legendVisible,
}: {
  planId: string;
  legendVisible: boolean;
}) => {
  const [plan, setPlan] = useState<PlanDetails>();
  const [categoryPlans, setCategoryPlans] = useState<ICategoryPlan[]>();

  const getSeatingPlanDetails = async () => {
    const mappingRequest = {
      planId: planId,
      venueId: undefined,
    };

    try {
      const response = await getSeatingPlanDetailsApi(mappingRequest);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setPlan(response.seatingPlanDetails);
        // console.log("seatingPlanDetails", response.seatingPlanDetails);

        let catPlans = [];
        const seatPlan = response.seatingPlanDetails.sectionSeatResponses;

        let start = 0;
        let end = 0;
        let cat;
        for (let i = 0; i < seatPlan.length - 1; i++) {
          let r = [];
          let c = [];
          if (i === 0) {
            start = 0;
            end = seatPlan[0].sectionRow;
          } else {
            start = seatPlan[i - 1].sectionRow;
            end = seatPlan[i].sectionRow;
          }

          for (let m = start; m <= end; m++) {
            r.push(m);
          }

          cat = {
            desc: seatPlan[i].seatSectionDescription,
            price: seatPlan[i].seatPrice,
            row: r,
          };

          catPlans.push(cat);
        }

        start = seatPlan[seatPlan.length - 2].sectionRow;
        end = seatPlan[seatPlan.length - 1].sectionRow;
        let r = [];
        for (let m = start; m <= end; m++) {
          r.push(m);
        }
        cat = {
          desc: seatPlan[seatPlan.length - 1].seatSectionDescription,
          price: seatPlan[seatPlan.length - 1].seatPrice,
          row: r,
        };
        catPlans.push(cat);

        setCategoryPlans(catPlans);
      } else if (
        response.statusCode === "200" &&
        response.message === "NO MATCHING SEATING PLAN"
      ) {
        console.log("NO MATCHING SEATING PLAN");
      }
    } catch (err) {
      console.log("SEATING PLAN DETAILS " + err);
    }
  };

  useEffect(() => {
    getSeatingPlanDetails();
  }, []);

  const options: Options = {
    map: {
      rows: 30,
      columns: 10,
      seatTypes: {
        default: {
          label: "Default",
          cssClass: `${styles.default}`,
          price: 10,
        },
        cat1: {
          label: "Category 1",
          cssClass: `${styles.cat1}`,
          price: categoryPlans ? categoryPlans[0].price : 0,
          seatRows: [0, 1],
          // seats: [{ row: 0, col: 1 }],
        },
        cat2: {
          label: "Category 2",
          cssClass: `${styles.cat2}`,
          price: categoryPlans ? categoryPlans[1].price : 0,
          seatRows: [2, 3, 4, 5],
          // seats: [{ row: 0, col: 0 }],
        },
        cat3: {
          label: "Category 3",
          cssClass: `${styles.cat3}`,
          price: categoryPlans ? categoryPlans[2].price : 0,
          seatRows: categoryPlans ? categoryPlans[2].row : [],
        },
        cat4: {
          label: "Category 4",
          cssClass: `${styles.cat4}`,
          price: categoryPlans ? categoryPlans[3].price : 0,
          seatRows: categoryPlans ? categoryPlans[3].row : [],
        },
        cat5: {
          label: "Category 5",
          cssClass: `${styles.cat5}`,
          price: categoryPlans ? categoryPlans[4].price : 0,
          seatRows: categoryPlans ? categoryPlans[4].row : [],
        },
        cat6: {
          label: "Category 6",
          cssClass: `${styles.cat6}`,
          price: 200,
        },
      },
      disabledSeats: [
        { row: 0, col: 0 },
        { row: 0, col: 9 },
      ],
      columnSpacers: [2, 8],
      rowSpacers: [6],
      // seatLabel: (index: SeatIndex) => {
      //   return `Row ${index.row + 1}, Seat ${index.col + 1}`;
      // },
    },
    cart: {
      visible: false,
      currency: "$",
    },
    legendVisible: legendVisible,
  };

  const seatchartRef = useRef<SeatchartJS>();

  const handleClick = () => {
    // const index = { row: 0, col: 6 };
    // const seat = seatchartRef.current?.getSeat(index);

    // seatchartRef.current?.setSeat(index, {
    //   state: seat?.state === 'selected' ? 'available' : 'selected',
    // });
    const selectedSeats = seatchartRef.current?.getCart();
  };

  return (
    <div>
      {/* <h2 className={styles.stage}>STAGE</h2> */}
      {/* <button onClick={handleClick}>Toggle Seat</button> */}
      {categoryPlans && <Seatchart ref={seatchartRef} options={options} />}
    </div>
  );
};

export default SeatingPlanOne;
