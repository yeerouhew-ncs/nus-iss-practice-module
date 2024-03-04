import React, { useEffect, useState } from "react";
import Header from "../../../common/header/Header";
import styles from "./PlanCreate.module.scss";
import { useNavigate } from "react-router-dom";

const PlanCreate = () => {
    const navigate = useNavigate();

    const createAddPlanDto = () => {
        // DUMMY FUNCTION - TO BE REPLACED WITH ACTUAL FUNCTION
        var formData = new FormData();

        formData.append(
            "planId",
            (document.getElementById("planId") as HTMLInputElement).value
        );
        formData.append(
            "planFileName",
            (document.getElementById("planFileName") as HTMLInputElement).value
        );
        formData.append(
            "venueId",
            (document.getElementById("venueId") as HTMLInputElement).value
        );

        var jsonStr = JSON.stringify(Object.fromEntries(formData));
        console.log("JSON STRING", jsonStr);
        alert("Mock create plan: check console log for JSON!");
    }

    // const navigateBack = () => {
    //     navigate("/event", { replace: true });
    // };

    return (
        <div>
            <div className={`row ${styles.planHeader}`}>
                <div className="col-md-12">
                <h2>Add New Layout</h2>
                </div>
            </div>
            <div className="form-group row">
                <form>
                <div className="col-md-12 mb-3">
                    <label className="form-label">Layout ID</label>
                    <input type="text" id="planId" className="form-control" value="3" disabled></input> 
                    {/* placeholder; value should be auto-increment from DB */}
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
            </div>
        </div>
    );
}

export default PlanCreate;