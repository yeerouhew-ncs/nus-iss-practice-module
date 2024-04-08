import React from "react";
import { Step, StepLabel, Stepper } from "@mui/material";

interface BreadcrumbProps {
  activeStep: number;
}

const Breadcrumb: React.FC<BreadcrumbProps> = ({ activeStep }) => {
  const steps = ["Seat Selection", "Confirmation", "Payment", "Finish"];

  return (
    <div>
      <Stepper activeStep={activeStep} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </div>
  );
};

export default Breadcrumb;
