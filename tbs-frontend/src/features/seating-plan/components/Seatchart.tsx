import React, { forwardRef, useEffect, useRef } from "react";
import SeatchartJS, { Options } from "seatchart";
import styles from "./Seatchart.module.scss";
import "seatchart/dist/seatchart.min.css";

interface SeatchartProps {
  options: Options;
}

function setForwardedRef<T>(ref: React.ForwardedRef<T>, value: T) {
  if (typeof ref === "function") {
    ref(value);
  } else if (ref) {
    ref.current = value;
  }
}

const Seatchart = forwardRef<SeatchartJS | undefined, SeatchartProps>(
  ({ options }, ref) => {
    const seatchartRef = useRef<SeatchartJS>();
    const elementRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
      if (elementRef.current && !seatchartRef.current) {
        seatchartRef.current = new SeatchartJS(elementRef.current, options);

        setForwardedRef(ref, seatchartRef.current);

        return () => {
          seatchartRef.current = undefined;
          setForwardedRef(ref, undefined);
        };
      }
    }, []);

    return (
      <div
        ref={elementRef}
        className={styles.container}
        style={{ width: "100%", height: "100%" }}
      />
    );
  }
);

export default Seatchart;
