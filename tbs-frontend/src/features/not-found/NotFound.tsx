import React from "react";
import { useParams } from "react-router";

const NotFound = () => {
  const path = useParams();

  console.log(path);
  return (
    <div
      style={{ border: "1px solid red", backgroundColor: "red", color: "#fff" }}
    >
      <h2>Page Not Found</h2>
    </div>
  );
};

export default NotFound;
