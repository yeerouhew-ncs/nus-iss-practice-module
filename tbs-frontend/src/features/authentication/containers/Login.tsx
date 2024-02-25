import React from "react";
import styles from "./Login.module.scss";

const Login = () => {
  return (
    <div>
      <div className={styles.loginWrapper}>
        <div className={styles.loginHeader}>
          {/* <h1>Ticket Booking System</h1> */}
          <h2>Login</h2>
        </div>

        <div className={styles.loginContainer}>
          <input
            type="text"
            placeholder="ID"
            className={`form-control rounded-0 border-0 border-bottom ${styles.loginInput}`}
            style={{ marginBottom: "10px" }}
            // value={loginId}
            // onChange={e => setLoginId(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            className={`form-control rounded-0 border-0 border-bottom ${styles.loginInput}`}
            style={{ marginBottom: "10px" }}
            // value={loginId}
            // onChange={e => setLoginId(e.target.value)}
          />
          <div>
            <button
              type="submit"
              className={`btn ${styles.primaryBtn} btn-sm`}
              style={{ width: "100%" }}
            >
              Login
            </button>
          </div>
          {/* <div style={{ color: 'red', textAlign: 'left' }}>{loginError !== '' && <ErrorBar errorMsg={loginError} />}</div> */}
        </div>
      </div>
    </div>
  );
};

export default Login;
