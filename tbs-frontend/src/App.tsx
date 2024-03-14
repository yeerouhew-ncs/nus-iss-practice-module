import React, { useEffect, useState } from "react";
import "./App.css";
import Header from "./common/header/Header";
import { BrowserRouter } from "react-router-dom";
import UserContext, { useAuthContext } from "./context/AuthContext";
import { getUserInfo } from "./features/authentication/authentication.api";
import { UserDetails } from "./interfaces/authentication-interface";
import AppRoutes from "./routes";

function App() {
  // const [userInfo, setUserInfo] = useState<UserDetails>();

  const { token, user } = useAuthContext();

  const retrieveUserInfo = async () => {
    try {
      if (!token) {
        return;
      }

      const response = await getUserInfo(token);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        // setUserInfo(response.userDetails);
        user(response.userDetails);
      }
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    retrieveUserInfo();
  }, [token]);

  return (
    <BrowserRouter>
      {token ? <Header /> : ""}
      <div className="wrapper">
        <AppRoutes />
      </div>
    </BrowserRouter>
  );
}

export default App;
