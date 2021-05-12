import React, { useState, useEffect } from "react";
import GoogleLogin from "react-google-login"
import { Link } from "react-router-dom";
import Modal from "react-modal";
import logo from "../hotpoll-logo.svg";
import AuthService from '../services/user.service'
import { useGlobalContext } from "../context";
import Loading from './Loading'



const OAUTH_URL = "http://localhost:4444/oauth2/authorization/google";

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
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [isRegistration,setIsRegistration] = useState(false);

  const {logged, setLogged} = useGlobalContext();


  useEffect(() => {
    if(AuthService.getCurrentUser())
    {
      setLogged(true);
    }
  })

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsRegistration(false);
    setLogin('');
    setEmail('');
    setPassword('');
    setPasswordConfirm('');
    setIsModalOpen(false);
  };
  const logOut = () => {
    AuthService.logout();
    setLogged(false);
    // location.reload();
    //albo to:
    window.location.assign("/#"); 
  }

  const handleLoginSubmit = (event) => {
    setLoading(true);
    event.preventDefault();
    console.log("clicked login button");
    AuthService.login(login,password).then(
      ()=>{
        setLoading(false);
        setLogged(true);
        console.log("udalo sie zalogowac");
        window.location.assign("/#/createpoll"); 
        closeModal();
      }
      ,
      (loginFailedError) => {
        //wyswietlic blad ze cos sie nie zgadza 
        //np brak uzytkownika czy zle haslo
        setLoading(false);
        console.log("nie udalo sie zalogowac");
        console.log(loginFailedError);
      }
    )

  };

  const handleRegisterSubmit = (event) => 
  {
    // podstawowe sprawdzanie typu: czy jest poprawy email wpisany EDIT: o przy submicie samo sprawdza
    // czy zgadzaja sie oba hasla
    // jezeli cos jest nietak to trzeba na czerwono wyswietlic komunikat



    setLoading(true);
    event.preventDefault();
    console.log("clicked register button");
    AuthService.register(login,email,password).then(
      ()=>{
        setLoading(false);
        console.log("udalo sie zarejestrowac");
        // wyswietlic komunikat ze sie udalo zarejestowac i mozna sie zalogowac
        // to mozna na zielono
        setIsRegistration(false);
      }
      ,
      (registerFailedError) => {
        // wyswietlic blad na czerwono ze cos nietak (np jest taki uzytkownik)
        setLoading(false);
        console.log("nie udalo sie zarejestrowac");
        console.log(registerFailedError);
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
              <button onClick={logged?logOut:openModal} className="link-underline">
                {logged?"Logout":"Sign in"}
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
          <form onSubmit={isRegistration?handleRegisterSubmit:handleLoginSubmit} className="loginForm">
            <input
                className={isRegistration?"registration-form":"hidden"}
                type="email"
                placeholder="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
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
              <input
                className={isRegistration?"registration-form":"hidden"}
                type="password"
                placeholder="confirm password"
                value={passwordConfirm}
                onChange={(e) => setPasswordConfirm(e.target.value)}
              />
            {
              loading?<Loading/>:
            
            <button type="submit">
              {isRegistration?"Register":"Log in"}
            </button>
        }
          </form>
          <h1>
            {isRegistration?"Already have an account?":"Don't Have an Account?"}
          </h1>
          <div className="create-acc-or-guest">
            <button id="register-btn" onClick={()=>{
              setIsRegistration(!isRegistration);
            }}>
              {isRegistration?"Log in":"sign up"}
              </button>
            <button id="enter-as-guest-btn">
              Enter as a Guest
            </button>
          
            <a className="google-btn" href={OAUTH_URL} target="popup">
                <img src='../google_button.png' alt="Sign in with google" />
            </a>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default Navbar;
