import { useEffect, useState } from "react";
import styles from "./EventEdit.module.scss";
import { useNavigate, useParams } from "react-router-dom";
import { useForm, UseFormRegister } from "react-hook-form";
import { editEventApi, getEventDetailsApi } from "../event.api";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import moment from "moment";
import { twoWeekslater } from "../../../utils/date-utils";
// import { getSeatingPlanListApi } from "../../seating-plan/seating-plan.api";
import {
  IGetPlanDetailsRequest,
  PlanList,
} from "../../../interfaces/seating-plan-interface";
import { EventResponse } from "../../../interfaces/event-interface";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import PlanViewModal from "./PlanViewModal";
import { Category } from "../../plan/containers/admin-container/PlanCreate";
import { getPlanDetailsApi } from "../../plan/plan.api";
import { List } from "reactstrap";
import { useAuthContext } from "../../../context/AuthContext";

type SeatingPlanType = {
  row: number;
  col: number;
  planName: string;
  venueName: string;
  sectionSeats: Category[];
};

const EventEdit: React.FC = () => {
  const navigate = useNavigate();
  const param = useParams();
  const { userInfo } = useAuthContext();
  const eventId = param?.eventId;

  const [event, setEvent] = useState<EventResponse>();
  const [planList, setPlanList] = useState<PlanList[]>();
  const [error, setErrors] = useState<boolean>(false);
  const [formErrors, setFormErrors] = useState<Array<String>>();
  const [fromDt, setFromDt] = useState<any>();
  const [toDt, setToDt] = useState<any>();

  const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
  const [selectedPlanLayout, setSelectedPlanLayout] =
    useState<SeatingPlanType>();
  const [selectedPlan, setSelectedPlan] = useState<PlanList>();

  // react-hook-form
  const form: {
    register: UseFormRegister<any>;
    // handleSubmit: (arg0: (data: any) => void) => React.FormEventHandler<HTMLFormElement>;
    getValues: any;
    setValue: any;
    watch: any;
    reset: any;
  } = useForm();

  const [watchEventFromDt, watchEventToDt] = form.watch([
    "eventFromDt",
    "eventToDt",
  ]);

  // const getListEvent = async () => {
  //   try {
  //     const response = await getSeatingPlanListApi();
  //     if (response.statusCode === "200" && response.message === "SUCCESS") {
  //       setPlanList(response.seatingPlanList);
  //       console.log("response.seatingPlanList", response.seatingPlanList);
  //     }
  //   } catch (err) {
  //     setErrors(true);
  //   }
  // };

  const getEventDetails = async () => {
    try {
      const mappingRequest = {
        eventId: eventId,
        subjectId: undefined,
      };
      const response = await getEventDetailsApi(mappingRequest);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setEvent(response.eventDetails);
        console.log("response.eventDeatils", response.eventDetails);
      }
    } catch (err) {
      setErrors(true);
    }
  };

  useEffect(() => {
    // getListEvent();
    getEventDetails();
    console.log("twoWeeksLater", twoWeekslater());
  }, []);

  const createEditEventDto = async () => {
    const formEventName =
      ((document.getElementById("eventName") as HTMLInputElement)?.value ||
        event?.eventName) ??
      "";
    const formArtistName =
      ((document.getElementById("artistName") as HTMLInputElement)?.value ||
        event?.artistName) ??
      "";
    const formEventFromDt =
      ((document.getElementById("eventFromDt") as HTMLInputElement)?.value
        ? moment(
            (document.getElementById("eventFromDt") as HTMLInputElement)?.value,
            "DD/MM/YYYY"
          ).format("YYYY-MM-DD HH:mm:ss")
        : moment(event?.eventFromDt).format("YYYY-MM-DD HH:mm:ss")) ?? "";
    const formEventToDt =
      ((document.getElementById("eventToDt") as HTMLInputElement)?.value
        ? moment(
            (document.getElementById("eventToDt") as HTMLInputElement)?.value,
            "DD/MM/YYYY"
          ).format("YYYY-MM-DD HH:mm:ss")
        : moment(event?.eventToDt).format("YYYY-MM-DD HH:mm:ss")) ?? "";

    var formData = new FormData();

    formData.append("eventName", formEventName);
    formData.append("artistName", formArtistName);
    formData.append("eventFromDt", formEventFromDt);
    formData.append("eventToDt", formEventToDt);
    formData.append("eventId", event?.eventId ?? "");
    formData.append("planId", event?.planId ?? "");

    var jsonStr = JSON.stringify(Object.fromEntries(formData));
    console.log("JSON STRING", jsonStr);
    console.log(localStorage.getItem("token"));

    const response = await editEventApi(localStorage.getItem("token"), jsonStr);

    if (response.statusCode === "200" && response.message === "SUCCESS") {
      alert("Event saved successfully");
      console.log("SUCCESS");
    } else {
      alert("Error saving event");
      console.log("FAIL");
    }

    if (userInfo?.authorities[0].authority === "ADMIN")
      navigate("/admin/event/list");
    else if (userInfo?.authorities[0].authority === "ORGANISER")
      navigate("/organiser/event/list");
  };

  const handleSubmit = () => {
    // clear old errors
    setFormErrors(new Array<String>());

    // check that dates are correct
    // if (moment((document.getElementById("eventFromDt") as HTMLInputElement)?.value, "DD/MM/YYYY") < )

    createEditEventDto();
  };

  const handleFromDateChange = (date: any) => {
    setFromDt(date);
    console.log("DATES", fromDt, toDt);
  };

  const handleToDateChange = (date: any) => {
    setToDt(date);
    console.log("DATES", fromDt, toDt);
  };

  const navigateBack = () => {
    if (userInfo?.authorities[0].authority === "ADMIN")
      navigate("/admin/event/list", { replace: true });
    else if (userInfo?.authorities[0].authority === "ORGANISER")
      navigate("/organiser/event/list", { replace: true });
  };

  const showModal = (plan: PlanList) => {
    console.log("isModalVisible", isModalVisible);
    setIsModalVisible(true);
    setSelectedPlan(plan);
  };

  const getPlanDetails = async () => {
    const mappedRequest: IGetPlanDetailsRequest = {
      planId: selectedPlan?.planId,
      venueId: undefined,
    };
    try {
      const response = await getPlanDetailsApi(mappedRequest);
      if (response.message === "SUCCESS" && response.statusCode === "200") {
        console.log(response.seatingPlanDetails);
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
        setSelectedPlanLayout(seatingPlan);
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  useEffect(() => {
    if (selectedPlan?.planId) getPlanDetails();
  }, [selectedPlan?.planId]);

  return (
    <div>
      {error && (
        <AlertPopUp
          type="danger"
          message="There's been an error while trying to retrieving data"
        />
      )}
      <div className={`row ${styles.eventHeader}`}>
        <div className="col-md-12">
          <h2>Edit Event</h2>
        </div>
      </div>

      <div className="form-group row">
        <form>
          <div className="col-md-12 mb-3">
            <label className="form-label">Event Name</label>
            <input
              type="text"
              id="eventName"
              className="form-control"
              placeholder={`${event?.eventName}`}
            ></input>
          </div>
          <div className="col-md-12 mb-3">
            <label className="form-label">Artist Name</label>
            <input
              type="text"
              id="artistName"
              className="form-control"
              placeholder={`${event?.artistName}`}
            ></input>
          </div>
          <div className="row row-md">
            <div className="col-md-6 mb-3">
              <label className="form-label">Event Start Date</label>
              <input
                type="text"
                className="form-control"
                value={`Current: ${moment(event?.eventFromDt).format(
                  "DD/MM/YYYY"
                )}`}
                disabled
              ></input>
              <LocalizationProvider dateAdapter={AdapterMoment}>
                <DatePicker
                  format="DD/MM/YYYY"
                  minDate={twoWeekslater()}
                  maxDate={toDt}
                  onChange={handleFromDateChange}
                  sx={{
                    width: "100%",
                    "& .MuiInputBase-input": {
                      height: "30px",
                      padding: "6px 12px",
                    },
                    "& .MuiInputBase-root": {
                      borderRadius: "var(--bs-border-radius)",
                      color: "var(--bs-body-color)",
                    },
                  }}
                  slotProps={{
                    textField: {
                      id: "eventFromDt",
                    },
                  }}
                />
              </LocalizationProvider>
            </div>
            <div className="col-md-6 mb-3">
              <label className="form-label">Event End Date</label>
              <input
                type="text"
                className="form-control"
                value={`Current: ${moment(event?.eventToDt).format(
                  "DD/MM/YYYY"
                )}`}
                disabled
              ></input>
              <LocalizationProvider dateAdapter={AdapterMoment}>
                <DatePicker
                  format="DD/MM/YYYY"
                  minDate={fromDt}
                  onChange={handleToDateChange}
                  sx={{
                    width: "100%",
                    "& .MuiInputBase-input": {
                      height: "30px",
                      padding: "6px 12px",
                    },
                    "& .MuiInputBase-root": {
                      borderRadius: "var(--bs-border-radius)",
                      color: "var(--bs-body-color)",
                    },
                  }}
                  slotProps={{
                    textField: {
                      id: "eventToDt",
                    },
                  }}
                />
              </LocalizationProvider>
            </div>
          </div>
          <div className="col-md-12 mb-3">
            <label className="form-label">Seating Layout</label>
            <div className="row">
              {planList &&
                planList.length > 0 &&
                planList.map((plan, index) => (
                  <div className={`col-md-6 ${styles.planCard}`}>
                    <input
                      type="radio"
                      className="form-radio"
                      name="planId"
                      id="exampleRadios1"
                      value={plan.planId}
                      defaultChecked
                    />
                    <label className={styles.layersMenu}>
                      {/* <div className={styles.layoutDesc} onClick={showModal}> */}
                      {plan.venueName + ": " + plan.planName}
                      {/* </div> */}
                    </label>
                    <button
                      type="button"
                      className={`${styles.layoutBtn}`}
                      onClick={() => showModal(plan)}
                    >
                      View Layout
                    </button>
                    <PlanViewModal
                      isModalVisible={isModalVisible}
                      setIsModalVisible={setIsModalVisible}
                      planId={plan.planId}
                      selectedPlanLayout={selectedPlanLayout}
                      setSelectedPlanLayout={setSelectedPlanLayout}
                    />
                  </div>
                ))}
            </div>
          </div>
          <div className={`row`}>
            <div className={`col-md-12 ${styles.createBtnGroup}`}>
              <button
                type="button"
                onClick={navigateBack}
                className={`btn btn-primary ${styles.primaryBtn}`}
              >
                Back
              </button>
              <button
                type="button"
                onClick={handleSubmit}
                className={`btn btn-primary ${styles.primaryBtn}`}
              >
                Submit
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EventEdit;
