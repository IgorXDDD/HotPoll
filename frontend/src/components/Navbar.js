import React, { useState } from "react";
import { Link } from "react-router-dom";
import Modal from "react-modal";
import logo from "../hotpoll-logo.svg";
import AuthService from '../services/user.service'
import { useGlobalContext } from "../context";
import Loading from './Loading'

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
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  
  const {logged, setLogged} = useGlobalContext();

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };


  const handleSubmit = (event) => {
    setLoading(true);
    event.preventDefault();
    console.log("clicked login button");
    AuthService.login(login,password).then(
      ()=>{
        setLoading(false);
        console.log("udalo sie zalogowac");
        window.location.assign("/#/about"); 
        closeModal();
      }
      ,
      (loginFailedError) => {
        setLoading(false);
        console.log("nie udalo sie zalogowac");
        console.log(loginFailedError);
      }
    )

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
        
        <div className="login-or-create-account">
          <h1>Login or Create Account for Free!</h1>
          <form onSubmit={handleSubmit} className="loginForm">
            <input
              type="text"
              placeholder="login"
              value={login}
              onChange={(e) => setLogin(e.target.value)}
            />
            <input
              type="password"
              placeholder="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            {
              loading?<Loading/>:
            
            <button type="submit">Login</button>
        }
          </form>
          <h1>Don't Have an Account?</h1>
          <div className="create-acc-or-guest">
            <button id="register-btn">Register</button>
            <button id="enter-as-guest-btn">
              Enter as a Guest
            </button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default Navbar;
