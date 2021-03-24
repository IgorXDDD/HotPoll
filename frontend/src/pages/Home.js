import React from "react";
import { Link } from "react-router-dom";
import Welcome from "../components/Welcome";
import LoginOrCreateAccount from "../components/LoginOrCreateAccount";
import Footer from "../components/Footer";

const Home = () => {
  return (
    <>
      <div className="homescreen">
        <Link to="/about">About</Link>
        <section className="homescreen-content">
          <Welcome />
          <LoginOrCreateAccount />
        </section>
      </div>
      <Footer />
    </>
  );
};

export default Home;
