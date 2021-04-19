import React from "react";
import { Link } from "react-router-dom";
import logo from "../hotpoll-logo.svg";

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="nav-center">
        <Link to="/">
          <img src={logo} alt="hotpoll logo" className="logo" />
        </Link>
        <ul className="nav-links">
          <li>
            <Link to="/" className="link-underline">
              Home
            </Link>
          </li>
          <li>
            <Link to="/about" className="link-underline">
              About
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
