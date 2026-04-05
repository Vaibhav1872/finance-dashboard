import { useState } from "react";
import { setAuth } from "../services/api";
import "./login.css";

function Login({ setLoggedIn }) {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const handleLogin = () => {

    setAuth(username, password);

    fetch("http://localhost:8080/auth/login", {
      headers: {
        Authorization: "Basic " + btoa(username + ":" + password),
      },
    })
      .then((res) => {
        if (res.status === 200) {

          const uname = username.toLowerCase();

          // ✅ FIXED ROLE LOGIC (IMPORTANT)
          if (uname.includes("admin")) {
            localStorage.setItem("role", "ROLE_ADMIN");
          } else if (uname.includes("analyst")) {
            localStorage.setItem("role", "ROLE_ANALYST");
          } else {
            localStorage.setItem("role", "ROLE_VIEWER");
          }

          localStorage.setItem("loggedIn", "true");
          setLoggedIn(true);

          // ✅ ensure UI updates immediately
          window.location.reload();

        } else {
          alert("Invalid credentials");
        }
      })
      .catch(() => {
        alert("Server error");
      });
  };

  return (
    <div className="login-container">
      <div className="login-box">

        <h2>Finance Dashboard</h2>

        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <div className="password-box">
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <span
            className="toggle"
            onClick={() => setShowPassword(!showPassword)}
          >
            {showPassword ? "🙈" : "👁️"}
          </span>
        </div>

        <button onClick={handleLogin}>Login</button>

      </div>
    </div>
  );
}

export default Login;