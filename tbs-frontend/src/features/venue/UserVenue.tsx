import React, { useEffect, useState } from "react";
import styles from "./UserVenue.module.scss";
import { Controller, FieldValues, useForm } from "react-hook-form";
import { ErrorBar } from "../../common/error-bar/ErrorBar";

import AlertPopUp from "../../common/alert-popup/AlertPopUp";
import { useNavigate } from "react-router-dom";
import {searchVenueAPI} from "./venue.api";
import {IVenueRequest, VenueResponse} from "../../interfaces/venue-interface";

const UserVenue = () => {
  const [venues, setVenues] = useState<VenueResponse[]>([]);
  const [error, setErrors] = useState<boolean>(false);


  const imageBasePath =
    window.location.protocol + "//" + window.location.host + "/";

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
      venueName: "",
      venueAddress: "",
    },
  });

  const handleSearchTermOnChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: FieldValues
  ) => {
    let { value, name } = e.target;

    value = value.toUpperCase();

    field.onChange(value);
  };

  const handleClearOnClick = () => {
    setValue("venueAddress", "");
    setValue("venueName", "");
    clearErrors();
  };

  const handleSearchOnClick = async (data: any) => {
    const mapResult:IVenueRequest = {
      venueName: data.venueName,
      venueAddress: data.venueAddress,
    };
    try {
      const response = await searchVenueAPI(mapResult);

      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setVenues(response.venueList);
      }
    } catch (error) {
      setErrors(true);
    }
  };

  const onErrors = () => {
    setErrors(true);
  };

  useEffect(() => {
    const onLoadVenues = async () => {
      const mappingResult = {
        venueName: null,
        venueAddress: null,
      };

      try {
        const response = await searchVenueAPI(mappingResult);
        if (response.statusCode === "200" && response.message === "SUCCESS") {
          setVenues(response.venueList);
        }

        // TODO: handle errors
      } catch (error) {
        setErrors(true);
      }
    };

    onLoadVenues();
  }, []);



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
          <h2>Venues</h2>
        </div>
      </div>
      <div className={styles.searchContainer}>
        <div className={`row form-group`}>
            <div className={`col-md-2 ${styles.searchRow}`}>
              <label htmlFor="eventName" className="form-contol-label">
                Venue Name
              </label>
            </div>
            <div className="col-md-4">
              <Controller
                name="venueName"
                control={control}
                rules={{
                  validate: {},
                }}
                render={({ field }) => (
                  <input
                    {...field}
                    type="text"
                    className="form-control"
                    id="venueName"
                    name="venueName"
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                      handleSearchTermOnChange(e, field);
                    }}
                  />
                )}
              />
              {formState.errors?.venueName && (
                <ErrorBar errorMsg={formState.errors.venueName?.message} />
              )}
            </div>

            <div className={`col-md-2 ${styles.searchRow}`}>
              <label htmlFor="artistName" className="form-contol-label">
                Address
              </label>
            </div>
            <div className="col-md-4">
              <Controller
                  name="venueAddress"
                  control={control}
                  render={({ field }) => (
                      <input
                          {...field}
                          type="text"
                          className="form-control"
                          id="venueAddress"
                          name="venueAddress"
                          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                            handleSearchTermOnChange(e, field);
                          }}
                      />
                  )}
                />
                {formState.errors?.venueAddress && (
                    <ErrorBar errorMsg={formState.errors.venueAddress?.message} />
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
      {/*End of searchContainer */}
      {venues.length === 0 && (
        <div className={styles.eventItemList}>There is no matching venue.</div>
      )}

      <div className={`${styles.eventItemsRow} row`}>
        {venues.length > 0 &&
          venues.map((venue, index) => (
            <div
              key={index}
              className={`col-4 ${styles.eventCard}`}
              // onClick={() => navigateEventView(venue.venueId.toString())}
            >
              <img
                src={`${imageBasePath}/placeholder600x400.svg`}
                className={`${styles.placeholderImg}`}
              />
              <div className={`${styles.eventBody}`}>

                <div className={`${styles.eventTitle}`}>{venue.venueName}</div>
                <div className={`${styles.eventTitle}`}>{venue.venueAddress}</div>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default UserVenue;
