import React, {
  Dispatch,
  SetStateAction,
  useEffect,
  useRef,
  useState,
} from "react";
import SeatchartJS, { Options, SeatInfo, SeatState } from "seatchart";
import "./OrderSeatingPlan.scss";
import Seatchart from "./Seatchart";
import { GetSeatResponse } from "../../../interfaces/seating-plan-interface";
import { OrderType } from "../../event/containers/UserEventView";
import styles from "../../event/containers/UserEventView.module.scss";
import { useNavigate } from "react-router-dom";
import {
  EventDetailsResponse,
  EventResponse,
} from "../../../interfaces/event-interface";

/*
https://seatchart.js.org/classes/Seatchart.html
 */
type DisabledSeats = {
  row: number;
  col: number;
};

type SectionSeatType = {
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
  // setOrder?: Dispatch<SetStateAction<OrderType | undefined>>;
  event: EventDetailsResponse | undefined;
};
const OrderSeatingPlan: React.FC<SeatingPlanProps> = ({
  row,
  col,
  sectionSeats,
  isLegendVisible,
  columnSpacers,
  disabledSeats,
  rowSpacers,
  setSeatList,
  event,
}) => {
  console.log("SEATING PLAN ROW ", row, col);
  console.log("SEATIN PLAN SECTION SEATS ", sectionSeats);
  const seatchartRef = useRef<SeatchartJS>();
  const navigate = useNavigate();

  const [order, setOrder] = useState<OrderType>();
  const [isFullReservation, setIsFullReservation] = useState<boolean>(false);

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
      const seatRows = Array.from({ length: seatRow }, (_, i) => startRow + i);
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

  console.log("transformedSeats {}", transformedSeats);

  const options: Options = {
    map: {
      rows: row,
      columns: col,
      seatTypes: {
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
    console.log("SEATIN PLAN SECTION SEATS ", sectionSeats);

    // combine list of seats from each category
    const combinedSeatResponses = sectionSeats.reduce(
      (acc: GetSeatResponse[], section: SectionSeatType) =>
        acc.concat(section.seatResponses),
      []
    );

    console.log("COMBINED SEAT RESPONSE {}", combinedSeatResponses);

    // show seat availability on seatchart
    for (let seat of combinedSeatResponses) {
      const seatStatus = seat.seatStatus as SeatState;
      seatchartRef.current?.setSeat(
        { row: seat.seatRow, col: seat.seatCol },
        { state: seatStatus }
      );
    }

    // check thru all seats, if seat availability is reserved, should disable btn
    const isReserved = combinedSeatResponses.every(
      (seat) => seat.seatStatus === "reserved"
    );
    if (isReserved) {
      setIsFullReservation(true);
    }
  }, []);

  const navigateBack = () => {
    navigate("/user/event/list", { replace: true });
  };

  const navigateToOrder = () => {
    const selectedSeats = seatchartRef.current?.getCart();
    const totalPrice = seatchartRef.current?.getCartTotal();
    console.log("selectedSeats", selectedSeats);
    setOrder({
      orderSeatInfo: selectedSeats,
      orderTotalPrice: totalPrice,
      event: event,
    });

    navigate("/user/order/preview", {
      replace: true,
      state: {
        order: {
          orderSeatInfo: selectedSeats,
          orderTotalPrice: totalPrice,
          event: event,
        },
      },
    });
  };

  return (
    <div>
      <Seatchart ref={seatchartRef} options={options} />
      <div></div>
      <div className={styles.viewBtnGroup}>
        <button
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          onClick={navigateBack}
        >
          Back
        </button>
        <button
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          onClick={navigateToOrder}
          disabled={isFullReservation}
        >
          Proceed
        </button>
      </div>
    </div>
  );
};

export default OrderSeatingPlan;
