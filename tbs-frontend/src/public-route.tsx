import React from "react";
import { useAuthContext } from "./context/AuthContext";
import { Navigate, Outlet } from "react-router-dom";

const PublicRoute = () => {
  const { userInfo } = useAuthContext();

  return userInfo ? <Navigate to="event/list" /> : <Outlet />;
};

export default PublicRoute;
