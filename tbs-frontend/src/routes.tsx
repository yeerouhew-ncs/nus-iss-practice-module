import React from "react";
import { Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import EventCreate from "./features/event/containers/EventCreate";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/event" element={<Event />}></Route>
      <Route path="/eventCreate" element={<EventCreate />}></Route>
    </Routes>
  );
};

export default AppRoutes;
