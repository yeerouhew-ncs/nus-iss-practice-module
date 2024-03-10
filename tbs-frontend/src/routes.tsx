import React from "react";
import { Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import EventCreate from "./features/event/containers/EventCreate";
import EventView from "./features/event/containers/EventView";
import PlanCreate from "./features/plan/containers/PlanCreate";
import PlanEdit from "./features/plan/containers/PlanEdit";
import SeatingPlanOne from "./features/seating-plan/containers/SeatingPlanOne";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/event" element={<Event />} />
      <Route path="/eventCreate" element={<EventCreate />} />
      <Route path="/eventView/:eventId" element={<EventView />} />
      {/* <Route path="/seating-plan" element={<SeatingPlanOne />} /> */}
      <Route path="/planCreate" element={<PlanCreate />} />
      <Route path="/planEdit" element={<PlanEdit />} />
    </Routes>
  );
};

export default AppRoutes;
