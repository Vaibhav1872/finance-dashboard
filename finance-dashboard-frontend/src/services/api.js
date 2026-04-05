import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

export const setAuth = (username, password) => {
  const token = btoa(username + ":" + password);

  localStorage.setItem("auth", token);
  API.defaults.headers.common["Authorization"] = "Basic " + token;
};

const token = localStorage.getItem("auth");
if (token) {
  API.defaults.headers.common["Authorization"] = "Basic " + token;
}

export default API;