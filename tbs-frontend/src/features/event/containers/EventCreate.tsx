import React, { useEffect, useState } from "react";
import Header from "../../../common/header/Header";
import styles from "./EventCreate.module.scss";
import { useNavigate } from "react-router-dom";
import { addEventApi } from "../event.api";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import moment from "moment";

const EventCreate = () => {
    const navigate = useNavigate();

    const createAddEventDto = async () => {
        alert("Event saved successfully");
        var formData = new FormData;
        
        formData.append("eventName", (document.getElementById("eventName") as HTMLInputElement).value);
        formData.append("artistName", (document.getElementById("artistName") as HTMLInputElement).value);
        formData.append("eventFromDt", moment((document.getElementById("eventFromDt") as HTMLInputElement).value, "DD/MM/YYYY").format("YYYY-MM-DD HH:mm:ss"));
        formData.append("eventToDt", moment((document.getElementById("eventToDt") as HTMLInputElement).value, "DD/MM/YYYY").format("YYYY-MM-DD HH:mm:ss"));
        formData.append("planId", (document.querySelector('input[name="planId"]:checked') as HTMLInputElement).value);
        // NEED TO IMPLEMENT SUBJECT ID BELOW
        formData.append("subjectId", "null");

        var jsonStr = JSON.stringify(Object.fromEntries(formData));
        console.log("JSON STRING", jsonStr);
        
        const response = await addEventApi(jsonStr);

        if (response.statusCode === "200" && response.message === "SUCCESS") {
            console.log("SUCCESS");
        } else {
            console.log("FAIL");
        }

        navigate("/event");
    }


    return (
        <div>
            <div className={`row ${styles.eventHeader}`}>
                <div className="col-md-10">
                    <h2>Create An Event</h2>
                </div>
            </div>
            <div className="form-group row">
                <form>
                    <div className="col-md-8 mb-3">
                        <label className="form-label">Event Name</label>
                        <input type="text" id="eventName" className="form-control"></input>
                    </div>
                    <div className="col-md-8 mb-3">
                        <label className="form-label">Artist Name</label>
                        <input type="text" id="artistName" className="form-control"></input>
                    </div>
                    <div className="row row-md">
                        <div className="col-md-4 mb-3">
                            <label className="form-label">Event Start Date</label>
                            <LocalizationProvider dateAdapter={AdapterMoment}>
                                <DatePicker
                                    format="DD/MM/YYYY"
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
                        <div className="col-md-4 mb-3">
                            <label className="form-label">Event End Date</label>
                            <LocalizationProvider dateAdapter={AdapterMoment}>
                                <DatePicker
                                    format="DD/MM/YYYY"
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
                    <div className="col-md-8 mb-3">
                        <label className="form-label">Seating Layout</label>
                        <div className="row">
                            <div className={`col-md-6 ${styles.planCard}`}>
                                <label className="layersMenu">
                                    <input type="radio" className="form-radio" name="planId" id="exampleRadios1" value="1" defaultChecked></input>
                                        <img className={`${styles.planThumbnail}`} src={require("../../../images/mock_layout.png")} alt="layout1" />
                                        <div>Layout 1</div>
                                </label>  
                            </div>  
                            <div className={`col-md-6 ${styles.planCard}`}>
                                <label className="layersMenu">
                                    <input type="radio" className="form-radio" name="planId" id="exampleRadios2" value="2" />
                                        <img className={`${styles.planThumbnail}`} src={require("../../../images/mock_layout.png")} alt="layout2" />
                                        <div>Layout 2</div>
                                </label>  
                            </div>  
                        </div>
                    </div>
                    <button type="button" onClick={createAddEventDto} className={`btn btn-primary ${styles.primaryBtn}`}>Submit</button>
                </form>
            </div>
        </div>
    );
}

export default EventCreate;