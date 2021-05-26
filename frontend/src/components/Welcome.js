import React from "react";
import { Link } from "react-router-dom";
import {useGlobalContext} from "../context";

const Welcome = () => {
  const {logged} = useGlobalContext();
  return (
    <div className="welcome">
      <h1>HotPoll</h1>
      <h2>where you can find web's hottest polls</h2>
      <Link className="link-to-about link-underline" to="/about">
        About
      </Link>
      <br />
      <Link className={logged?"link-to-about link-underline":"hidden"}
      to="/createpoll">
        Create new poll
      </Link>
    </div>
  );
};

export default Welcome;
