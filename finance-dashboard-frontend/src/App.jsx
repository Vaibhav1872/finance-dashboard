import { useState } from "react";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";

function App() {

  const [loggedIn, setLoggedIn] = useState(
    localStorage.getItem("loggedIn") === "true"
  );

  return (
    <>
      {!loggedIn
        ? <Login setLoggedIn={setLoggedIn} />
        : <Dashboard setLoggedIn={setLoggedIn} />
      }
    </>
  );
}

export default App;