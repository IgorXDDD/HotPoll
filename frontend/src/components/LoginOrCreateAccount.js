import React from "react";
import { useState } from "react";

const testUrl = "localhost:4444/test";

const LoginOrCreateAccount = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [testData, setTestData] = useState(null);

  const fetchData = async () => {
    try {
      const response = await fetch(testUrl);
      const data = await response.json();
      setTestData(data);
      console.log(testData);
    } catch (error) {
      console.log("Error with fetching test data: " + error);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("clicked login button");
  };

  const handleEnterAsGuest = () => {
    console.log("clicked enter as a guest button");
    fetchData();
  };

  return (
    <div className="login-or-create-account">
      <h1>Login Or Create Account Component</h1>
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
        <button className="dark-btn" type="submit">
          Login
        </button>
      </form>
      <h1>Don't Have an Account?</h1>
      <div className="create-acc-or-guest">
        <button className="register-btn light-btn">Register</button>
        <h2>or</h2>
        <button
          className="enter-as-guest-btn light-btn"
          onClick={handleEnterAsGuest}
        >
          Enter as a Guest
        </button>
      </div>
    </div>
  );
};

export default LoginOrCreateAccount;
