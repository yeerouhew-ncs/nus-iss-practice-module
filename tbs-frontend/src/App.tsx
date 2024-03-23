import "./App.css";
import { BrowserRouter } from "react-router-dom";
import AppRoutes from "./routes";

function App() {
  return (
    <BrowserRouter>
      <div className="wrapper">
        <AppRoutes />
      </div>
    </BrowserRouter>
  );
}

export default App;
