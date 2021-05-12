import React from "react";
import { useState } from "react";
import { useGlobalContext } from "../context";

const testUrl = "http://localhost:4444/test?text=OK";

const LoginOrCreateAccount = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  const {logged, setLogged} = useGlobalContext();

  const fetchData = () => {
    fetch(testUrl)
      .then((response) => response.json())
      .then((data) => console.log(JSON.stringify(data)));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("clicked login button");
  };

  const handleEnterAsGuest = () => {
    console.log("clicked enter as a guest button");
    // setTestData("OK");
    fetchData();
  };

  return (
    <div className="login-or-create-account">
      <h1>Login or Create Account for Free!</h1>
      <form onSubmit={handleSubmit} className="loginForm">
        <input
          type="text"
          placeholder="login"
          value={login}
          onChange={(e) => setLogin(e.target.value)}
        />
        <input
          type="password"
          placeholder="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
      <h1>Don't Have an Account?</h1>
      <div className="create-acc-or-guest">
        <button id="register-btn">Register</button>
        <button id="enter-as-guest-btn" onClick={handleEnterAsGuest}>
          Enter as a Guest
        </button>
      </div>
    </div>
  );
};

export default LoginOrCreateAccount;
