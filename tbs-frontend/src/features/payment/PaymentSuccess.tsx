import Breadcrumb from "../../common/breadcrumb/Breadcrumb";
import React from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {OrderType} from "../event/containers/UserEventView";
import {useAuthContext} from "../../context/AuthContext";
import moment from "moment/moment";
import styles from "./OrderPreview.module.scss";

const PaymentSuccess: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { userInfo } = useAuthContext();

    const order: OrderType = location.state?.order;
    return (
        <div>
            <Breadcrumb activeStep={3} />
            <br />
            <div>
                <div className={` ${styles.eventViewHeader}`}>Payment Successful</div>
            </div>
            <div className={styles.eventViewContainer}>
                <div className={`text-center`}>
                    <div className={`col-lg-3 col-4 mx-auto p-4 mt-5 mb-3`}>
                        <img src={require('../../images/payment_success.png')} className={`img-fluid`} />
                    </div>
                    <h3 className={`p-3`}><strong>Payment Successful</strong></h3>
                </div>
                <div>
                    <div className={`${styles.header}`}>Contact Details</div>
                </div>
                <div className={`${styles.section}`}>
                    <div className={`row ${styles.detailsRow}`}>
                        <div className={`${styles.title} col-3`}>Full Name</div>
                        <div className={`${styles.value} col-9`}>{userInfo?.fullName}</div>
                    </div>
                    <div className={`row ${styles.detailsRow}`}>
                        <div className={`${styles.title} col-3`}>Email</div>
                        <div className={`${styles.value} col-9`}>{userInfo?.email}</div>
                    </div>
                </div>
                <div>
                    <div className={`${styles.header}`}>Payment Method</div>
                </div>
                <div className={`${styles.section}`}>
                    <div className={`row ${styles.detailsRow}`}>
                        <div className={`${styles.title} col-3`}>
                            <input
                                type="radio"
                                className="form-radio"
                                name="payment"
                                id="radio-1"
                                //   value={plan.planId}
                                defaultChecked
                            />
                            <label className={`${styles.value}`}>
                                <div> Paypal</div>
                            </label>
                        </div>
                    </div>
                </div>

                <div className="table-responsive">
                    <table className={`table table-striped ${styles.orderTable}`}>
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th>Seat Info</th>
                            <th>Ticket Info</th>
                        </tr>
                        </thead>
                        <tbody>
                        {order.orderSeatInfo?.map((seat, index) => (
                            <tr key={index}>
                                <td>
                                    <div className={`${styles.itemHeader}`}>
                                        {order.event?.eventName}
                                    </div>
                                    <div className={`${styles.itemDate}`}>
                                        {moment(order.event?.eventFromDt).format("DD/MM/YYYY")}
                                    </div>
                                </td>
                                <td>
                                    <div className={`${styles.itemValue}`}>Seat {seat.label}</div>
                                </td>
                                <td>
                                    <div className={`${styles.itemValue}`}>{seat.type}</div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                        <tfoot>
                        <tr className={`${styles.footerHeader}`}>
                            <td colSpan={3}>Total: $ {order.orderTotalPrice}</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default PaymentSuccess;