import React, { useEffect, useMemo, useState } from "react";
import "./App.css";
import Header from "./common/header/Header";
import { BrowserRouter } from "react-router-dom";
import UserContext, { useAuthContext } from "./context/AuthContext";
import { getUserInfo } from "./features/authentication/authentication.api";
import { UserDetails } from "./interfaces/authentication-interface";
import AppRoutes from "./routes";
import { AuthProvider } from "./context/AuthProvider";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="wrapper">
          <AppRoutes />
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
