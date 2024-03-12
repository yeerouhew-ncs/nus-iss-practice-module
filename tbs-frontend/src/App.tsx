import React from "react";
import "./App.css";
import Header from "./common/header/Header";
import { BrowserRouter } from "react-router-dom";
import AppRoutes from "./routes";
import UserContext from "./context/UserContext";

function App() {
  // const value = localStorage.getItem("user");
  const value = false;

  return (
    <UserContext.Provider value={{ value }}>
      <BrowserRouter>
        {value ? <Header /> : ""}
        <div className="wrapper">
          <AppRoutes />
        </div>
      </BrowserRouter>
    </UserContext.Provider>
  );
}

export default App;
