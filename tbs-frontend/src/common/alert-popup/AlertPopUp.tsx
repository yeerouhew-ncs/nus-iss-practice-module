import React, { useState, useEffect } from "react";
import { Alert } from "reactstrap";
import styles from "./AlertPopUp.module.scss";

const AlertPopUp = ({ type, message }: { type: string; message: string }) => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisible(false);
    }, 5000);

    return () => clearTimeout(timer);
  }, []);

  let alertClass = "custom-fade-alert";

  if (type === "danger") {
    alertClass += " alert-danger";
  } else if (type === "warning") {
    alertClass += " alert-warning";
  }

  if (!visible) {
    return null;
  }

  return (
    <div className={styles.alertWrapper}>
      <Alert className={alertClass} style={{ fontSize: "0.9em" }}>
        {message}
      </Alert>
    </div>
  );
};

export default AlertPopUp;
