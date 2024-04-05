import { useNavigate } from "react-router-dom";


const PaymentStart: React.FC = () => {
    const navigate = useNavigate();

    const redirectToPaypal = () => {
        navigate("/user/payment/success");
    }

    return (
        <div>
            <h2>Payment Options</h2>
            <div className={`d-grid gap-2 col-6 mx-auto pt-5`}>
                <button className={`btn btn-outline-light`} onClick={redirectToPaypal}>
                    <img src={require('../../images/paypal.png')} className={`img-fluid`} />
                </button>
            </div>
        </div>
    );
}

export default PaymentStart;