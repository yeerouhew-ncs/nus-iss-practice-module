import React from "react";

const PermissionDenied: React.FC = () => {
  return (
    <div
      style={{ border: "1px solid red", backgroundColor: "red", color: "#fff" }}
    >
      <h2>Permission denied</h2>
    </div>
  );
};

export default PermissionDenied;
