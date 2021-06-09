import React from 'react';
// import LoginOrCreateAccount from "../components/LoginOrCreateAccount";
// import Footer from "../components/Footer";
import PollList from '../components/PollList';
// import PollCreator from "../components/pollsUtilities/PollCreator";
// import Loading from "../components/Loading";

const Home = () => {
  return (
    <>
      <div className="homescreen">
        {/* <section className='homescreen-content'>
          <Welcome />
          <LoginOrCreateAccount />
        </section> */}
        <PollList />
      </div>
      {/* <PollList className={pollOnly?'':'hidden'} /> */}
      {/* <PollCreator />
      <PollList /> */}
    </>
  );
};

export default Home;
