import React from "react";
import { Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="event" element={<Event />}></Route>
    </Routes>
  );
};

export default AppRoutes;
