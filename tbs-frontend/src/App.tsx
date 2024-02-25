import React from "react";
import "./App.css";
import Header from "./common/header/Header";
import { BrowserRouter } from "react-router-dom";
import AppRoutes from "./routes";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="wrapper">
        <AppRoutes />
      </div>
    </BrowserRouter>
  );
}

export default App;
