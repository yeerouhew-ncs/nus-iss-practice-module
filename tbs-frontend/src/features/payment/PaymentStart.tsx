import { useNavigate } from "react-router-dom";
import {client} from "./payment.api";
import React from 'react';
import { PayPalButton } from "react-paypal-button-v2";

const PaymentStart: React.FC = () => {
    const navigate = useNavigate();

    const redirectToPaypal = () => {
        navigate("/user/payment/success");
    }

    const onSuccess = (payment:any) => {
        // Congratulation, it came here means everything's fine!
        console.log("The payment was succeeded!", payment);
        // this.props.clearCart();
        // this.props.history.push('/');
        // You can bind the "payment" object's value to your state or props or whatever here, please see below for sample returned data
    }
    const onCancel = (data:any) => {
        // User pressed "cancel" or close Paypal's popup!
        console.log('The payment was cancelled!', data);
        // You can bind the "data" object's value to your state or props or whatever here, please see below for sample returned data
    }
    const onError = (err:any) => {
        // The main Paypal's script cannot be loaded or somethings block the loading of that script!
        console.log("Error!", err);
        // Because the Paypal's main script is loaded asynchronously from "https://www.paypalobjects.com/api/checkout.js"
        // => sometimes it may take about 0.5 second for everything to get set, or for the button to appear
    }
    let currency = 'SGD';
    let total = '10.00';

    return (
        <div>
            <h2>Payment Options</h2>
            <div className={`d-grid gap-2 col-6 mx-auto pt-5`}>
                {/*<button className={`btn btn-outline-light`} onClick={redirectToPaypal}>*/}
                {/*    <img src={require('../../images/paypal.png')} className={`img-fluid`} />*/}
                {/*</button>*/}
                <PayPalButton
                    amount={total}
                    currency={currency}
                    options={client}
                    shippingPreference="NO_SHIPPING"
                    onError={onError}
                    onSuccess={onSuccess}
                    // onCancel={onCancel}
                />
            </div>
        </div>
    );
}

export default PaymentStart;