import {useLocation, useNavigate} from "react-router-dom";
import {client} from "./payment.api";
import React, {useState} from 'react';
import { PayPalButton } from "react-paypal-button-v2";
import {OrderType} from "../event/containers/UserEventView";
import {IQueueRequest2, IQueueResponse} from "../../interfaces/queue-interface";
import {joinQueue} from "../queue/queue.api";
import {IOrderRequest, IOrderResponse} from "../../interfaces/order-interface";
import {useAuthContext} from "../../context/AuthContext";
import moment from "moment/moment";
import {addOrder} from "../seating-plan/order.api";
import AlertPopUp from "../../common/alert-popup/AlertPopUp";
import Breadcrumb from "../../common/breadcrumb/Breadcrumb";
import styles from "../event/containers/UserEventView.module.scss";

const PaymentStart: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const order: OrderType = location.state?.order;
    console.log("Order labels : ",order?.orderSeatInfo?.map(({label})=>label));
    const { userInfo } = useAuthContext();
    const [error, setErrors] = useState<boolean>(false);

    const redirectToPaypal = () => {
        navigate("/user/payment/success", {
            replace: true,
            state: {
                order,
            },
            });
    };

    const saveOrder = async () => {
        console.log("userinfo")
        const mappedRequest: IOrderRequest = {
            totalPrice: order?.orderTotalPrice,
            eventId: order?.event?.eventId,
            subjectId: userInfo?.id,
            seatNames: order?.orderSeatInfo?.map(({label})=>label),
            orderDt: moment(new Date()).format("yyyy-MM-DD HH:mm:ss"),
            orderStatus: "COMPLETED"
        };
        console.log("mapped request: ",mappedRequest);
        try {
            const response: IOrderResponse = await addOrder(mappedRequest);
            console.log("IOrderResponse: ",response);

            if (response.message === "SUCCESS" ) {
                console.log("message: ",response);
                redirectToPaypal();


            }
        } catch (error) {
            // TODO: error handling
            console.log("error",error);

        }
    };
    const onSuccess = (payment:any) => {
        // Congratulation, it came here means everything's fine!
        console.log("The payment was succeeded!", payment);
        saveOrder().then(r => console.log(r,"Success payment and order saved"));
        // this.props.clearCart();
        // this.props.history.push('/');
        // You can bind the "payment" object's value to your state or props or whatever here, please see below for sample returned data
    }
    const onCancel = (data:any) => {
        // User pressed "cancel" or close Paypal's popup!
        console.log('The payment was cancelled!', data);
        alert("The payment was cancelled! Please try again");
        // You can bind the "data" object's value to your state or props or whatever here, please see below for sample returned data
    }
    const onError = (err:any) => {
        // The main Paypal's script cannot be loaded or somethings block the loading of that script!
        console.log("Error!", err);
        alert("There is an error :"+err);
        setErrors(true);
        // Because the Paypal's main script is loaded asynchronously from "https://www.paypalobjects.com/api/checkout.js"
        // => sometimes it may take about 0.5 second for everything to get set, or for the button to appear
    }
    let currency = 'SGD';

    return (
        <div>
            <Breadcrumb activeStep={2} />
            <br />


            {error && (
                <AlertPopUp
                    type="danger"
                    message="There's been an error while trying to pay data pls try again later"
                />
            )}
            <div>
                <div className={` ${styles.eventViewHeader}`}>Payment Options</div>
            </div>
            <div className={styles.eventViewContainer}>
                <div className={`row`}>
                    {/*<button className={`btn btn-outline-light`} onClick={redirectToPaypal}>*/}
                {/*    <img src={require('../../images/paypal.png')} className={`img-fluid`} />*/}
                {/*</button>*/}
                <PayPalButton
                    amount={order.orderTotalPrice}
                    currency={currency}
                    options={client}
                    shippingPreference="NO_SHIPPING"
                    // createOrder={saveOrder}
                    onError={onError}
                    onSuccess={onSuccess}
                    // onCancel={onCancel}
                />
            </div>
            </div>

        </div>
    );
}

export default PaymentStart;