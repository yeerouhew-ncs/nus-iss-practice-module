import React, { useEffect, useMemo } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import EventCreate from "./features/event/containers/EventCreate";
import EventView from "./features/event/containers/EventView";
import PlanCreate from "./features/plan/containers/PlanCreate";
import PlanEdit from "./features/plan/containers/PlanEdit";
import SeatingPlanOne from "./features/seating-plan/containers/SeatingPlanOne";
import { useAuthContext } from "./context/AuthContext";
import PlanCreatePreview from "./features/plan/containers/PlanCreatePreview";
import Plan from "./features/plan/containers/Plan";
import PlanView from "./features/plan/containers/PlanView";
import { getUserInfo } from "./features/authentication/authentication.api";
import Header from "./common/header/Header";
import PrivateRoute from "./private-route";
import PublicRoute from "./public-route";

const AppRoutes = () => {
  const { token, userInfo, user } = useAuthContext();

  const retrieveUserInfo = async () => {
    try {
      if (!token) {
        return;
      }

      const response = await getUserInfo(token);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        user(response.userDetails);
        console.log("userapi", response.userDetails);
      }
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    retrieveUserInfo();
  }, []);

  return (
    <>
      {token ? <Header /> : ""}
      <Routes>
        <Route path="/" element={<PrivateRoute />}>
          <Route path="event/list" element={<Event />} />
          <Route path="event/view/:eventId" element={<EventView />} />
          <Route path="event/create" element={<EventCreate />} />

          <Route path="plan/list" element={<Plan />} />
          <Route path="plan/create" element={<PlanCreate />} />
          <Route path="plan/preview" element={<PlanCreatePreview />} />
          <Route path="plan/view/:planId" element={<PlanView />} />
          <Route path="plan/edit/:planId" element={<PlanView />} />
        </Route>

        <Route path="login" element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
        </Route>
      </Routes>
      {/* <Routes>
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
      </Routes> */}
    </>
  );
};

export default AppRoutes;
