import React from "react";
import Welcome from "../components/Welcome";
import LoginOrCreateAccount from "../components/LoginOrCreateAccount";
import Footer from "../components/Footer";
import PollList from "../components/PollList";
import PollCreator from "../components/polls/PollCreator";
// import Loading from "../components/Loading";

const Home = () => {
  return (
    <>
      <div className="homescreen">
        <section className="homescreen-content">
          <Welcome />
          {/* <LoginOrCreateAccount /> */}
        </section>
      </div>
      {/* <PollList /> */}
      {/* <PollCreator />
      <PollList /> */}
      <PollList />
    </>
  );
};

export default Home;
