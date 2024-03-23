import React, { Dispatch, SetStateAction, useEffect, useRef } from "react";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import SeatchartJS, { Options, SeatInfo } from "seatchart";
import "./SeatingPlan.scss";
import Seatchart from "./Seatchart";

/*
https://seatchart.js.org/classes/Seatchart.html
 */
type DisabledSeats = {
  row: number;
  col: number;
};

type SeatingPlanProps = {
  row: number;
  col: number;
  sectionSeats: Category[];
  isLegendVisible: boolean;
  columnSpacers?: number[];
  disabledSeats?: DisabledSeats[];
  rowSpacers?: number[];
  setSeatList?: Dispatch<SetStateAction<SeatInfo[]>>;
};
const SeatingPlan = ({
  row,
  col,
  sectionSeats,
  isLegendVisible,
  columnSpacers,
  disabledSeats,
  rowSpacers,
  setSeatList,
}: SeatingPlanProps) => {
  console.log("SEATING PLAN ROW ", row, col);
  const seatchartRef = useRef<SeatchartJS>();

  const transformedSeats = sectionSeats.reduce(
    (acc: any, seat: any, index: number) => {
      const { sectionDesc, seatPrice, sectionRow } = seat;
      const label = sectionDesc;
      const cssClass = `cat${index + 1}`;
      const price = parseInt(seatPrice);
      const seatRow = parseInt(sectionRow);
      // console.log("seatRow", seatRow);
      const startRow =
        acc
          .flatMap(({ seatRows }: { seatRows: any }) => seatRows)
          .slice(-1)[0] + 1 || 0;
      // console.log("startRow", startRow);
      const seatRows = Array.from({ length: seatRow }, (_, i) => startRow + i);
      // console.log("seatRows", seatRows);
      acc.push({
        label,
        cssClass,
        price,
        seatRows,
      });

      return acc;
    },
    []
  );

  console.log("transformedSeats", transformedSeats);

  const options: Options = {
    map: {
      rows: row,
      columns: col,
      seatTypes: {
        default: {
          label: "Unassigned",
          cssClass: `default`,
          price: 10,
        },
        ...Object.fromEntries(
          Object.values(transformedSeats).map((seat: any, index: number) => [
            // `cat${index + 1}`,
            [`${seat.label}`],
            {
              label: seat.label,
              cssClass: `${seat.cssClass}`,
              price: seat.price,
              seatRows: seat.seatRows,
            },
          ])
        ),
      },
      disabledSeats: disabledSeats,
      columnSpacers: columnSpacers,
      rowSpacers: rowSpacers,
    },
    cart: {
      visible: false,
      currency: "$",
    },
    legendVisible: isLegendVisible,
  };

  const getAllSeatInfo = (): SeatInfo[] => {
    if (!seatchartRef.current) return [];

    const allSeatInfo: SeatInfo[] = [];
    const { rows, columns } = options.map;

    for (let row = 0; row < rows; row++) {
      for (let col = 0; col < columns; col++) {
        const seat = seatchartRef.current.getSeat({ row, col });
        if (seat) {
          allSeatInfo.push({
            type: seat.type,
            index: { row, col },
            state: seat.state,
            label: seat.label,
          });
        }
      }
    }

    return allSeatInfo;
  };

  useEffect(() => {
    if (setSeatList) {
      setSeatList(getAllSeatInfo);
      console.log("option", options);
    }
    // const selectedSeats = seatchartRef.current?.getCart();
  }, []);

  return (
    <div>
      <Seatchart ref={seatchartRef} options={options} />
    </div>
  );
};

export default SeatingPlan;
