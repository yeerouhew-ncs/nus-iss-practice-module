import React, { useEffect, useState } from "react";
import Header from "../../../common/header/Header";
import styles from "./PlanCreate.module.scss";
import { useNavigate } from "react-router-dom";

const PlanEdit = () => {
    const navigate = useNavigate();

    const submitEditPlan = () => {
        // DUMMY FUNCTION - TO BE REPLACED WITH ACTUAL FUNCTION
        var rowCount = document.getElementsByTagName("tr").length - 1;
        
        var dict = [];
        var i: number;
        for (i=0; i<rowCount; i++) {
            const cur = i+1;
            const SECTIONID = "sectionId_".concat(cur.toString());
            const TOTALSEATS = "totalSeats_".concat(cur.toString());
            const PRICE = "price_".concat(cur.toString());

            dict.push({
                "sectionId": (document.getElementById(SECTIONID) as HTMLInputElement).innerText,
                "totalSeats": (document.getElementById(TOTALSEATS) as HTMLInputElement).value ? (document.getElementById(TOTALSEATS) as HTMLInputElement).value : (document.getElementById(TOTALSEATS) as HTMLInputElement).placeholder,
                "price": (document.getElementById(PRICE) as HTMLInputElement).value ? (document.getElementById(PRICE) as HTMLInputElement).value : (document.getElementById(PRICE) as HTMLInputElement).placeholder
            });
        }

        for (let key in dict) {
            let value = dict[key];
            console.log("DICT", key, value);
        }

        alert("Mock edit plan: check console log for DICT values!");
    }


    return (
        <div>
            <div className={`row ${styles.planHeader}`}>
                <div className="col-md-12">
                <h2>Edit Pricing</h2>
                </div>
            </div>
            <div>
                <table className="table table-hover">
                    <tr>
                        <th>Section</th>
                        <th>Total Seats</th>
                        <th>Price Per Seat</th>
                    </tr>

                    {/* HARCODED */}
                    <tr>
                        <td id="sectionId_1" className="hidden">1</td>
                        <td>Cat 1</td>
                        <td>
                            <input type="text" id="totalSeats_1" className="form-control" placeholder="100"></input>
                        </td>
                        <td>
                            <input type="text" id="price_1" className="form-control" placeholder="350"></input>
                        </td>
                    </tr>
                    <tr>
                        <td id="sectionId_2" className="hidden">2</td>
                        <td>Cat 2</td>
                        <td>
                            <input type="text" id="totalSeats_2" className="form-control" placeholder="200"></input>
                        </td>
                        <td>
                            <input type="text" id="price_2" className="form-control" placeholder="250"></input>
                        </td>
                    </tr>
                    <tr>
                        <td id="sectionId_3" className="hidden">3</td>
                        <td>Cat 3</td>
                        <td>
                            <input type="text" id="totalSeats_3" className="form-control" placeholder="300"></input>
                        </td>
                        <td>
                            <input type="text" id="price_3" className="form-control" placeholder="150"></input>
                        </td>
                    </tr>
                </table>
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
                    onClick={submitEditPlan}
                    className={`btn btn-primary ${styles.primaryBtn}`}
                >
                    Submit
                </button>
                </div>
            </div>
        </div>
    );
}

export default PlanEdit;