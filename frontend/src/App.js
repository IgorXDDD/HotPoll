import React from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

// import pages
import Home from "./pages/Home";
import Error from "./pages/Error";
import About from "./pages/About";
import SinglePollPage from "./pages/SinglePollPage";
import PollCreator from "./pages/PollCreator"
// ...

// import components
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import PollList from "./components/PollList";
import TagList from "./components/TagList";
import { Search } from "./components/Search";
import SearchForm from "./pages/SearchForm";
import UserPage from "./pages/UserPage";
// ...

const App = () => {

  return (
    <div>
      <Router>
        <Navbar />
        <Switch>
          <Route exact path='/'>
            <Home />
          </Route>
          <Route path='/about'>
            <About />
          </Route>
          <Route path='/createpoll'>
            <PollCreator />
          </Route>
          <Route path='/poll/:id'>
            <SinglePollPage />
          </Route>

          <Route path='/tag/:id/:page'>
            <TagList />
          </Route>

          <Route path='/tag/:id'>
            <TagList />
          </Route>

          <Route path='/page/:id'>
            <PollList />
          </Route>

          <Route path='/user'>
            <UserPage />
          </Route>

          <Route path='/search'>
            <SearchForm />
          </Route>

          <Route path='*'>
            <Error />
          </Route>
        </Switch>
        <Footer />
      </Router>
    </div>
  )
};

export default App;
