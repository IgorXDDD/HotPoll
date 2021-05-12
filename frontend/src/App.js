import React from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

// import pages
import Home from "./pages/Home";
import Error from "./pages/Error";
import About from "./pages/About";
import SinglePoll from "./pages/SinglePoll";
import PollCreator from "./components/polls/PollCreator"
// ...

// import components
import Navbar from "./components/Navbar";
// ...

const App = () => {

  return (
    <div>
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          <Route path="/about">
            <About />
          </Route>
          <Route path="/createpoll">
            <PollCreator />
          </Route>
          <Route path="/poll/:id">
            <SinglePoll />
          </Route>
          <Route path="*">
            <Error />
          </Route>
        </Switch>
      </Router>
    </div>
  );
};

export default App;
