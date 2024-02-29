import React from "react";
import { Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import SeatingPlan from "./features/seating-plan/containers/SeatingPlan";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/event" element={<Event />}></Route>
      <Route path="/seating-plan" element={<SeatingPlan />}></Route>
    </Routes>
  );
};

export default AppRoutes;
