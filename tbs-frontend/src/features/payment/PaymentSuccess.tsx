const PaymentSuccess: React.FC = () => {
    return (
        <div>
            <div className={`text-center`}>
                <div className={`col-lg-3 col-4 mx-auto p-4 mt-5 mb-3`}>
                    <img src={require('../../images/payment_success.png')} className={`img-fluid`} />
                </div>
                <h3 className={`p-3`}><strong>Payment Successful</strong></h3>
                <h4>Your ticket details will be sent to you via email.</h4>
            </div>
        </div>
    );
}

export default PaymentSuccess;