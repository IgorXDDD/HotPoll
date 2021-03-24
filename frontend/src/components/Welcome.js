import React from "react";
import { Link } from "react-router-dom";

const Welcome = () => {
  return (
    <div className="welcome">
      <Link to="/">
        <h1>HotPoll</h1>
      </Link>
      <h2>where you can find web's hottest polls</h2>
    </div>
  );
};

export default Welcome;
