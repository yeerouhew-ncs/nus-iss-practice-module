import React from "react";
import { Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="event" element={<Event />}></Route>
    </Routes>
  );
};

export default AppRoutes;
