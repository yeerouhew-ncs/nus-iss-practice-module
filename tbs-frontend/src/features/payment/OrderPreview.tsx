import Breadcrumb from "../../common/breadcrumb/Breadcrumb";
import styles from "./OrderPreview.module.scss";
import { useAuthContext } from "../../context/AuthContext";
import { useLocation, useNavigate } from "react-router-dom";
import { OrderType } from "../event/containers/UserEventView";
import moment from "moment";

const OrderPreview = () => {
  const { userInfo } = useAuthContext();
  const location = useLocation();
  const navigate = useNavigate();

  const order: OrderType = location.state?.order;

  const navigateBack = () => {
    navigate("/user/event/view" + order.event?.eventId);
  };

  const navigateToPayment = () => {
    // NAVIGATE TO PAYMENT,
    // you can get order on payment page by putting this => const order: OrderType = location.state?.order
    // order consists of orderSeatInfo, orderTotalPrice and event details
    navigate("", {
      replace: true,
      state: {
        order,
      },
    });
  };

  return (
    <div>
      <Breadcrumb activeStep={1} />
      <br />
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

      <br />
      <br />

      <div className={styles.viewBtnGroup}>
        <div
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          onClick={navigateBack}
        >
          Back
        </div>
        <div
          className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
          onClick={navigateToPayment}
        >
          Proceed to Payment
        </div>
      </div>
    </div>
  );
};

export default OrderPreview;
