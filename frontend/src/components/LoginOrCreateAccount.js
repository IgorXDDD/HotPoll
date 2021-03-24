import React from "react";
import { useState } from "react";

const LoginOrCreateAccount = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
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
        <button className="enter-as-guest-btn light-btn">
          Enter as a Guest
        </button>
      </div>
    </div>
  );
};

export default LoginOrCreateAccount;
