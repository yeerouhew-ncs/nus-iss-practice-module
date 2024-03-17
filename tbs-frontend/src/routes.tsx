import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import EventCreate from "./features/event/containers/EventCreate";
import EventView from "./features/event/containers/EventView";
import PlanCreate from "./features/plan/containers/PlanCreate";
import PlanEdit from "./features/plan/containers/PlanEdit";
import SeatingPlanOne from "./features/seating-plan/containers/SeatingPlanOne";
import { useAuthContext } from "./context/AuthContext";
import PrivateRoute from "./private-route";
import PlanCreatePreview from "./features/plan/containers/PlanCreatePreview";
import Plan from "./features/plan/containers/Plan";
import PlanView from "./features/plan/containers/PlanView";

const AppRoutes = () => {
  const { token, userInfo } = useAuthContext();

  return (
    <Routes>
      <Route
        path="/"
        element={!token ? <Login /> : <Navigate to="/event/list" />}
        // element={<Login />}
      />
      <Route
        path="login"
        element={!token ? <Login /> : <Navigate to="/event/list" />}
      />

      <Route path="event">
        <Route
          path="list"
          element={
            <PrivateRoute authorities={["MOP", "ADMIN", "ORGANISER"]}>
              <Event />
            </PrivateRoute>
          }
        />
        <Route
          path="view/:eventId"
          element={
            <PrivateRoute authorities={["MOP", "ADMIN", "ORGANISER"]}>
              <EventView />
            </PrivateRoute>
          }
        />
        <Route
          path="create"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <EventCreate />
            </PrivateRoute>
          }
        />
      </Route>

      <Route path="plan">
        <Route
          path="list"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <Plan />
            </PrivateRoute>
          }
        />
        <Route
          path="create"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <PlanCreate />
            </PrivateRoute>
          }
        />
        <Route
          path="create/preview"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <PlanCreatePreview />
            </PrivateRoute>
          }
        />
        <Route
          path="view/:planId"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <PlanView />
            </PrivateRoute>
          }
        />
        <Route
          path="edit/:planId"
          element={
            <PrivateRoute authorities={["ADMIN", "ORGANISER"]}>
              <PlanEdit />
            </PrivateRoute>
          }
        />
      </Route>
      {/* <Route path="/seating-plan" element={<SeatingPlanOne />} /> */}
      {/* <Route path="/planCreate" element={<PlanCreate />} /> */}
      {/* <Route path="/planEdit" element={<PlanEdit />} /> */}
    </Routes>
  );
};

export default AppRoutes;
