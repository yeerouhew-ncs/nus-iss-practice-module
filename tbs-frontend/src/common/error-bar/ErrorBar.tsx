import { faCircleExclamation } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import styles from "./ErrorBar.module.scss";

interface ErrorBarProps {
  errorMsg: string | undefined;
}

export const ErrorBar: React.FC<ErrorBarProps> = ({
  errorMsg,
}: ErrorBarProps) => {
  return (
    <div className={styles.errorBar}>
      <FontAwesomeIcon
        icon={faCircleExclamation}
        className={styles.errorIcon}
      />
      <span role="errorBar" className={styles.errorMsg}>
        {errorMsg}
      </span>
    </div>
  );
};
