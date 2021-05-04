import React, { useState } from "react";
import { Link } from "react-router-dom";
import Modal from "react-modal";
import logo from "../hotpoll-logo.svg";

const customStyles = {
  overlay: {
    backgroundColor: "rgba(0, 0, 0, 0.75)",
  },
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    borderRadius: "0.5em",
    background: "#1b1b1b",
  },
};

// Make sure to bind modal to your appElement (https://reactcommunity.org/react-modal/accessibility/)
Modal.setAppElement(document.getElementById("root"));

const Navbar = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
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
            <li>
              <button onClick={openModal} className="link-underline">
                Sign in
              </button>
            </li>
          </ul>
        </div>
      </nav>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        closeTimeoutMS={300}
        style={customStyles}
        contentLabel="Sign in Modal"
      >
        <h2>Sign in</h2>
        <Link to="#" className="link-underline" onClick={closeModal}>
          close
        </Link>
        <form className="loginForm">
          <input type="text" placeholder="login" />
          <input type="password" placeholder="password" />
          <button>Login</button>
        </form>
      </Modal>
    </>
  );
};

export default Navbar;
