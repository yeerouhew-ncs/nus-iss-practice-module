import React, { useEffect, useMemo, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Event from "./features/event/containers/Event";
import Login from "./features/authentication/containers/Login";
import EventCreate from "./features/event/containers/EventCreate";
import EventView from "./features/event/containers/EventView";
import PlanCreate from "./features/plan/containers/admin-container/PlanCreate";
import PlanEdit from "./features/plan/containers/admin-container/PlanEdit";
import SeatingPlanOne from "./features/seating-plan/containers/SeatingPlanOne";
import AuthContext, { useAuthContext } from "./context/AuthContext";
import PlanCreatePreview from "./features/plan/containers/admin-container/PlanCreatePreview";
import Plan from "./features/plan/containers/admin-container/Plan";
import PlanView from "./features/plan/containers/admin-container/PlanView";
import { getUserInfo } from "./features/authentication/authentication.api";
import Header from "./common/header/Header";
import PrivateRoute from "./private-route";
import PublicRoute from "./public-route";
import PermissionDenied from "./features/permission-denied/PermissionDenied";
import { UserDetails } from "./interfaces/authentication-interface";
import NotFound from "./features/not-found/NotFound";
import AdminHeader from "./common/header/AdminHeader";
import OrganiserHeader from "./common/header/OrganiserHeader";

const AppRoutes = () => {
  const [user, setUser] = useState<UserDetails>();
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  const setAuth = (auth: boolean) => {
    setIsAuthenticated(auth);
  };

  const retrieveUserInfo = async (token: string | null) => {
    try {
      if (!token) {
        return;
      }
      const response = await getUserInfo(token);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        // user(response.userDetails);
        console.log("userapi", response.userDetails);
        setAuth(true);
        setUser(response.userDetails);
        // if (response.userDetails.authorities[0].authority === UserRole.ADMIN) {
        //   navigate("/event/list");
        // } else if (
        //   response.userDetails.authorities[0].authority === UserRole.ORGANISER
        // ) {
        //   navigate("/event/list");
        // } else {
        //   navigate("/event/list");
        // }
      } else {
        setAuth(false);
        // setErrorMsg(ErrorMessageConstants.FAIL_TO_SIGN_IN);
      }
    } catch (err) {
      console.log(err);
      setAuth(false);
      // setErrorMsg(err as string);
    }
  };

  useEffect(() => {
    retrieveUserInfo(localStorage.getItem("token"));
  }, [isAuthenticated]);

  useEffect(() => {
    console.log("user", user);
  }, [user]);

  return (
    <>
      <AuthContext.Provider value={{ userInfo: user }}>
        <Routes>
          <Route path="/" element={<Navigate replace to="/login" />} />
          <Route
            path="/login"
            element={
              !isAuthenticated ? (
                <Login setAuth={setAuth} />
              ) : (
                <Navigate
                  replace
                  to={`${
                    user?.authorities[0].authority === "ADMIN"
                      ? "/admin/event/list"
                      : user?.authorities[0].authority === "ORGANISER"
                      ? "/organiser/event/list"
                      : user?.authorities[0].authority === "MOP"
                      ? "/user/event/list"
                      : "/login"
                  }`}
                />
              )
            }
          />
          <Route element={<AdminHeader setAuth={setAuth} setUser={setUser} />}>
            <Route
              element={
                <PrivateRoute
                  isAuthenticated={isAuthenticated}
                  roleRequired="ADMIN"
                />
              }
            >
              {/* <Route path="/" element={<Event />} /> */}

              <Route path="/admin/event/list" element={<Event />} />

              <Route path="/admin/event/create" element={<EventCreate />} />
              <Route path="/admin/plan/list" element={<Plan />} />
              <Route path="/admin/plan/create" element={<PlanCreate />} />
              <Route
                path="/admin/plan/preview"
                element={<PlanCreatePreview />}
              />
              <Route path="/admin/plan/view/:planId" element={<PlanView />} />
              <Route path="/admin/plan/edit/:planId" element={<PlanEdit />} />
              {/* </Route>
            <Route element={<PrivateRoute roleRequired="MOP" />}> */}
              <Route
                path="/admin/event/view/:eventId"
                element={<EventView />}
              />
            </Route>
          </Route>

          <Route
            element={<OrganiserHeader setAuth={setAuth} setUser={setUser} />}
          >
            <Route
              element={
                <PrivateRoute
                  isAuthenticated={isAuthenticated}
                  roleRequired="ORGANISER"
                />
              }
            >
              {/* <Route path="/organiser/" element={<Event />} /> */}
              <Route path="/organiser/event/list" element={<Event />} />
            </Route>
          </Route>

          <Route path="denied" element={<PermissionDenied />} />

          <Route path="*" element={<NotFound />} />
        </Routes>
      </AuthContext.Provider>

      {/* <Routes>
        <Route
          path="/"
          element={!token ? <Login /> : <Navigate to="/admin/event/list" />}
          // element={<Login />}
        />
        <Route
          path="login"
          element={!token ? <Login /> : <Navigate to="/admin/event/list" />}
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
