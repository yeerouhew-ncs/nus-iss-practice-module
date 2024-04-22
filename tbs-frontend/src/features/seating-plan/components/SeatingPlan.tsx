import React, { Dispatch, SetStateAction, useEffect, useRef } from "react";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import SeatchartJS, { Options, SeatInfo, SeatState } from "seatchart";
import "./SeatingPlan.scss";
import Seatchart from "./Seatchart";
import { GetSeatResponse } from "../../../interfaces/seating-plan-interface";

/*
https://seatchart.js.org/classes/Seatchart.html
 */
type DisabledSeats = {
  row: number;
  col: number;
};

export type SectionSeatType = {
  sectionId?: number | null;
  sectionDesc: string;
  sectionRow: number;
  seatPrice: number;
  seatResponses: GetSeatResponse[];
};

type SeatingPlanProps = {
  row: number;
  col: number;
  sectionSeats: SectionSeatType[];
  isLegendVisible: boolean;
  columnSpacers?: number[];
  disabledSeats?: DisabledSeats[];
  rowSpacers?: number[];
  setSeatList?: Dispatch<SetStateAction<SeatInfo[]>>;
  isViewEvent?: boolean;
};
const SeatingPlan: React.FC<SeatingPlanProps> = ({
  row,
  col,
  sectionSeats,
  isLegendVisible,
  columnSpacers,
  disabledSeats,
  rowSpacers,
  setSeatList,
  isViewEvent = false,
}) => {
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
          price: 0,
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

    console.log("iS EVENT EVENT", isViewEvent);

    if (isViewEvent) {
      // combine list of seats from each category
      const combinedSeatResponses = sectionSeats.reduce(
        (acc: GetSeatResponse[], section: SectionSeatType) =>
          acc.concat(section.seatResponses),
        []
      );

      // show seat availability on seatchart
      for (let seat of combinedSeatResponses) {
        if (seat) {
          const seatStatus = seat.seatStatus as SeatState;
          seatchartRef.current?.setSeat(
            { row: seat.seatRow, col: seat.seatCol },
            { state: seatStatus }
          );
        }
      }
    }
  }, []);

  return (
    <div>
      <Seatchart ref={seatchartRef} options={options} />
    </div>
  );
};

export default SeatingPlan;
