import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Modal from 'react-modal';
import logo from '../hotpoll-logo.svg';
import AuthService from '../services/user.service';
import { useGlobalContext } from '../context';
import Loading from './Loading';

const OAUTH_URL = 'http://localhost:4444/oauth2/authorization/google';
const PRINCIPAL_URL = 'http://localhost:4444/api/user/principal';
const googleImage = 'http://localhost:4444/icon';
const DEBUG_URL = 'https://jsonplaceholder.typicode.com/albums';
const customStyles = {
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.75)',
  },
  content: {
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    borderRadius: '0.5em',
    background: '#1b1b1b',
  },
};

// Make sure to bind modal to your appElement (https://reactcommunity.org/react-modal/accessibility/)
Modal.setAppElement(document.getElementById('root'));

const Navbar = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [isRegistration, setIsRegistration] = useState(false);
  const {
    logged,
    setLogged,
    isGoogleLogged,
    setIsGoogleLogged,
    googleInfo,
    setGoogleInfo,
  } = useGlobalContext();

  useEffect(() => {
    if (AuthService.getCurrentUser()) {
      setLogged(true);
      console.log('NORMALNE LOGOWANIE');
    } else {
      console.log('GOOGLOWE LGOWANIE');
      if (!isGoogleLogged) getGoogleLogin();
    }
  }, [logged, isGoogleLogged]);

  const getGoogleInfo = () => {
    fetch(PRINCIPAL_URL)
      .then((response) => response.json())
      .then((json) => {
        setGoogleInfo({
          email: json.authorities[0].attributes.email,
          username: json.authorities[0].attributes.name,
        });
      })
      .catch((e) => {
        console.log('nie logujemy googlem');
      });
  };

  const getGoogleLogin = () => {
    fetch(PRINCIPAL_URL)
      .then((response) => response.json())
      .then((data) => {
        console.log('takie cookie:');
        console.log(document.cookie);
        setIsGoogleLogged(true);
        console.log('udalo sie zalogowac googlem :))');
        setLogged(true);
        // window.location.assign("/#/createpoll");
        closeModal();
        getGoogleInfo();
      })
      .catch((e) => {
        console.log('nie udalo sie zalogowac googlem :(');
      });
  };

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
    document.cookie =
      'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    setIsGoogleLogged(false);
    AuthService.logout();
    setLogged(false);
    window.location.assign('/#');
  };

  const handleLoginSubmit = (event) => {
    setLoading(true);
    event.preventDefault();
    console.log('clicked login button');
    AuthService.login(login, password).then(
      () => {
        setLoading(false);
        setLogged(true);
        console.log('udalo sie zalogowac');
        // window.location.assign("/#/createpoll");
        closeModal();
        window.location.assign('/#');
      },
      (loginFailedError) => {
        //wyswietlic blad ze cos sie nie zgadza
        //np brak uzytkownika czy zle haslo
        setLoading(false);
        console.log('nie udalo sie zalogowac');
        console.log(loginFailedError);
      }
    );
  };

  const showAll = () => {
    console.log('is logged  = ' + logged);
    console.log('is Googlelogged  = ' + isGoogleLogged);
    console.log('takie cookie:');
    console.log(document.cookie);
  };

  const handleRegisterSubmit = (event) => {
    // podstawowe sprawdzanie typu: czy jest poprawy email wpisany EDIT: o przy submicie samo sprawdza
    // czy zgadzaja sie oba hasla
    // jezeli cos jest nietak to trzeba na czerwono wyswietlic komunikat

    setLoading(true);
    event.preventDefault();
    console.log('clicked register button');
    AuthService.register(login, email, password).then(
      () => {
        setLoading(false);
        console.log('udalo sie zarejestrowac');
        // wyswietlic komunikat ze sie udalo zarejestowac i mozna sie zalogowac
        // to mozna na zielono
        setIsRegistration(false);
      },
      (registerFailedError) => {
        // wyswietlic blad na czerwono ze cos nietak (np jest taki uzytkownik)
        setLoading(false);
        console.log('nie udalo sie zarejestrowac');
        console.log(registerFailedError);
      }
    );
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
              <Link
                className={logged ? 'link-underline' : 'link-hidden'}
                to="/createpoll"
              >
                Create new poll
              </Link>
            </li>
            <li>
              <Link to="/about" className="link-underline">
                About
              </Link>
            </li>
            <li>
              <Link to="/Search" className="link-underline">
                Search
              </Link>
            </li>

            <li>
              <Link
                className={logged ? 'link-underline' : 'link-hidden'}
                to="/user"
              >
                User
              </Link>
            </li>

            <li>
              <button onClick={logged ? logOut : openModal}>
                {logged ? 'Logout' : 'Sign in'}
              </button>
            </li>
            {/* <li>
              <button
                onClick={() => {
                  console.log('JESTESMY NA STRONIE:');
                  console.log(window.location.href);
                  console.log('RESZTA:');
                  console.log(window.location);
                }}
              >
                debug
              </button>
            </li> */}
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
          <form
            onSubmit={isRegistration ? handleRegisterSubmit : handleLoginSubmit}
            className="loginForm"
          >
            <input
              className={isRegistration ? 'registration-form' : 'hidden'}
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
              className={isRegistration ? 'registration-form' : 'hidden'}
              type="password"
              placeholder="confirm password"
              value={passwordConfirm}
              onChange={(e) => setPasswordConfirm(e.target.value)}
            />
            {loading ? (
              <Loading />
            ) : (
              <button type="submit">
                {isRegistration ? 'Register' : 'Log in'}
              </button>
            )}
          </form>
          <h1>
            {isRegistration
              ? 'Already have an account?'
              : "Don't Have an Account?"}
          </h1>
          <div className="create-acc-or-guest">
            <button
              id="register-btn"
              onClick={() => {
                setIsRegistration(!isRegistration);
              }}
            >
              {isRegistration ? 'Log in' : 'sign up'}
            </button>
            <button id="enter-as-guest-btn">
              <a className="google-btn" href={OAUTH_URL}>
                Sign in with Google
              </a>
            </button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default Navbar;
