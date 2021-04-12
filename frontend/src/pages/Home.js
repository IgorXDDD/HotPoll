import React from "react";
import Welcome from "../components/Welcome";
import LoginOrCreateAccount from "../components/LoginOrCreateAccount";
import Footer from "../components/Footer";

const Home = () => {
  return (
    <>
      <div className="homescreen">
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
