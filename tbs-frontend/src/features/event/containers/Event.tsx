import React, { useEffect, useState } from "react";
import Header from "../../../common/header/Header";
import styles from "./Event.module.scss";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getEventListApi } from "../event.api";
import { setegid } from "process";
import { EventResponse } from "../../../interfaces/event-interface";
import EventItem from "../components/EventItem";
import { Controller, FieldValues, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { ErrorBar } from "../../../common/error-bar/ErrorBar";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import { DatePicker, DateTimePicker } from "@mui/x-date-pickers";
import { mapGetEventList } from "../../../mapper/event-mapper";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import { useAuthContext } from "../../../context/AuthContext";

const Event = () => {
  const [events, setEvents] = useState<EventResponse[]>([]);
  const [error, setErrors] = useState<boolean>(false);

  const { userInfo } = useAuthContext();

  const navigate = useNavigate();

  const {
    control,
    formState,
    handleSubmit,
    setValue,
    getValues,
    setError,
    clearErrors,
    register,
  } = useForm({
    mode: "onChange",
    defaultValues: {
      eventName: "",
      artistName: "",
      eventFromDt: null,
      eventToDt: null,
    },
  });

  useEffect(() => {
    const onLoadEvents = async () => {
      const mappingResult = {
        eventId: null,
        eventName: null,
        artistName: null,
        eventFromDt: null,
        eventToDt: null,
        planId: null,
        usesrId: null,
        page: 0,
      };

      try {
        const response = await getEventListApi(mappingResult);
        if (response.statusCode === "200" && response.message === "SUCCESS") {
          setEvents(response.eventList.content);
        }

        // TODO: handle errors
      } catch (error) {
        setErrors(true);
      }
    };

    onLoadEvents();
  }, []);

  const handleSearchTermOnChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: FieldValues
  ) => {
    let { value, name } = e.target;

    value = value.toUpperCase();

    field.onChange(value);
  };

  const redirectCreateOnClick = () => {
    navigate("/admin/event/create");
  };

  const handleClearOnClick = () => {
    setValue("eventName", "");
    setValue("artistName", "");
    setValue("eventFromDt", null);
    setValue("eventToDt", null);
    clearErrors();
  };

  const handleSearchOnClick = async (data: any) => {
    const mapResult = mapGetEventList(data, 0);

    try {
      const response = await getEventListApi(mapResult);

      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setEvents(response.eventList.content);
      }
    } catch (error) {
      setErrors(true);
    }
  };

  const onErrors = () => {
    setErrors(true);
  };

  const navigateEventView = (eventId: string) => {
    navigate("/admin/event/view/" + eventId);
  };

  return (
    <div>
      {error && (
        <AlertPopUp
          type="danger"
          message="There's been an error while trying to retrieving data"
        />
      )}
      <div className={`${styles.eventHeader}`}>
        <div>
          <h2>Upcoming Events</h2>
        </div>
        <div>
          <button
            type="button"
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={redirectCreateOnClick}
            hidden={userInfo?.authorities[0].authority === "MOP"}
          >
            <span>Create an Event</span>
          </button>
        </div>
      </div>
      <div className={styles.searchContainer}>
        <div className={`row form-group`}>
          <div className={`col-md-2 ${styles.searchRow}`}>
            <label htmlFor="eventName" className="form-contol-label">
              Event Name
            </label>
          </div>
          <div className="col-md-4">
            <Controller
              name="eventName"
              control={control}
              rules={{
                validate: {},
              }}
              render={({ field }) => (
                <input
                  {...field}
                  type="text"
                  className="form-control"
                  id="eventName"
                  name="eventName"
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                    handleSearchTermOnChange(e, field);
                  }}
                />
              )}
            />
            {formState.errors?.eventName && (
              <ErrorBar errorMsg={formState.errors.eventName?.message} />
            )}
          </div>

          <div className={`col-md-2 ${styles.searchRow}`}>
            <label htmlFor="artistName" className="form-contol-label">
              Artist Name
            </label>
          </div>
          <div className="col-md-4">
            <Controller
              name="artistName"
              control={control}
              render={({ field }) => (
                <input
                  {...field}
                  type="text"
                  className="form-control"
                  id="artistName"
                  name="artistName"
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                    handleSearchTermOnChange(e, field);
                  }}
                />
              )}
            />
            {formState.errors?.artistName && (
              <ErrorBar errorMsg={formState.errors.artistName?.message} />
            )}
          </div>
        </div>

        <div className="form-group row">
          <div className={`col-md-2 ${styles.searchRow}`}>
            <label htmlFor="eventFromDt" className="form-contol-label">
              Event Start Date
            </label>
          </div>
          <div className={`col-md-4`}>
            <Controller
              name="eventFromDt"
              control={control}
              rules={{
                validate: {},
              }}
              render={({ field }) => (
                <LocalizationProvider dateAdapter={AdapterMoment}>
                  <DatePicker
                    format="DD/MM/YYYY"
                    {...field}
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
              )}
            />
            {formState.errors?.eventFromDt && (
              <ErrorBar errorMsg={formState.errors.eventFromDt?.message} />
            )}
          </div>

          <div className={`col-md-2 ${styles.searchRow}`}>
            <label htmlFor="eventToDt" className="form-contol-label">
              Event End Date
            </label>
          </div>
          <div className={`col-md-4`}>
            <Controller
              name="eventToDt"
              control={control}
              rules={{
                validate: {},
              }}
              render={({ field }) => (
                <LocalizationProvider dateAdapter={AdapterMoment}>
                  <DatePicker
                    format="DD/MM/YYYY"
                    {...field}
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
              )}
            />
            {formState.errors?.eventToDt && (
              <ErrorBar errorMsg={formState.errors.eventToDt?.message} />
            )}
          </div>
        </div>

        <div className={styles.searchBtnRow}>
          <button
            type="button"
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={handleClearOnClick}
          >
            <span>Clear All</span>
          </button>
          <button
            type="submit"
            className={`btn ${styles.primaryBtn} btn-sm`}
            onClick={handleSubmit(handleSearchOnClick, onErrors)}
          >
            <span>Search</span>
          </button>
        </div>
      </div>

      {events.length === 0 && (
        <div className={styles.eventItemList}>There is no matching event.</div>
      )}

      {events.length > 0 && (
        <div>
          <div className={styles.eventItemListHeader}>
            <div className={`row ${styles.eventDateTimeHeaderRow}`}>
              <div className={`col-md-3 ${styles.eventDateTimeHeaderCol}`}>
                Event Date
              </div>
              <div className={`col-md-5 ${styles.eventDateTimeHeaderCol}`}>
                Event Name
              </div>
              <div className={`col-md-4 ${styles.eventDateTimeHeaderCol}`}>
                Artist Name
              </div>
            </div>
          </div>

          <div className={styles.eventItemList}>
            {events &&
              events.map((eventInfo, index) => (
                <div
                  key={index}
                  onClick={() => navigateEventView(eventInfo.eventId)}
                >
                  <EventItem eventInfo={eventInfo} />
                </div>
              ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default Event;
