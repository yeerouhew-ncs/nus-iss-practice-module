import React, { useState, useEffect } from "react";
import { Alert } from "reactstrap";
import styles from "./AlertPopUp.module.scss";

const AlertPopUp = ({
  type,
  message,
  duration = 5000,
}: {
  type: string;
  message: string;
  duration: number;
}) => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisible(false);
    }, duration);

    return () => clearTimeout(timer);
  }, [duration]);

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
