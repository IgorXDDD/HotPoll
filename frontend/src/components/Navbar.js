import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import Modal from 'react-modal'
import logo from '../hotpoll-logo.svg'
import AuthService from '../services/user.service'
import { useGlobalContext } from '../context'
import Loading from './Loading'

const OAUTH_URL = 'http://localhost:4444/oauth2/authorization/google'
const PRINCIPAL_URL = 'http://localhost:4444/api/user/principal'
const DEBUG_URL = 'https://jsonplaceholder.typicode.com/albums'
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
}

// Make sure to bind modal to your appElement (https://reactcommunity.org/react-modal/accessibility/)
Modal.setAppElement(document.getElementById('root'))

const Navbar = () => {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [login, setLogin] = useState('')
  const [password, setPassword] = useState('')
  const [passwordConfirm, setPasswordConfirm] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [isRegistration, setIsRegistration] = useState(false)
  const {
    logged,
    setLogged,
    isGoogleLogged,
    setIsGoogleLogged,
    googleInfo,
    setGoogleInfo,
  } = useGlobalContext()

  useEffect(() => {
    if (AuthService.getCurrentUser()) {
      setLogged(true)
      console.log('NORMALNE LOGOWANIE')
    } 
    else {
      console.log('GOOGLOWE LGOWANIE')
      if (!isGoogleLogged) getGoogleLogin()
    }
  }, [logged, isGoogleLogged])

  const getGoogleInfo = () => {
    fetch(PRINCIPAL_URL)
      .then((response) => response.json())
      .then((json) => {
        setGoogleInfo({
          email: json.authorities[0].attributes.email,
          username: json.authorities[0].attributes.name,
        })
      }).catch(e=>{
        console.log("nie logujemy googlem");
      })
  }

  const getGoogleLogin = () => {
    fetch(PRINCIPAL_URL)
      .then((response) => response.json())
      .then((data) => {
        console.log('takie cookie:')
        console.log(document.cookie)
        setIsGoogleLogged(true)
        console.log('udalo sie zalogowac googlem :))')
        setLogged(true)
        // window.location.assign("/#/createpoll");
        closeModal()
        getGoogleInfo()
      })
      .catch((e) => {
        console.log('nie udalo sie zalogowac googlem :(')
      })
  }

  const openModal = () => {
    setIsModalOpen(true)
  }

  const closeModal = () => {
    setIsRegistration(false)
    setLogin('')
    setEmail('')
    setPassword('')
    setPasswordConfirm('')
    setIsModalOpen(false)
  }

  const logOut = () => 
  {
    document.cookie =
      'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'
    setIsGoogleLogged(false)
    AuthService.logout()
    setLogged(false)
    window.location.assign('/#')
  }

  const handleLoginSubmit = (event) => {
    setLoading(true)
    event.preventDefault()
    console.log('clicked login button')
    AuthService.login(login, password).then(
      () => {
        setLoading(false)
        setLogged(true)
        console.log('udalo sie zalogowac')
        // window.location.assign("/#/createpoll");
        closeModal()
        window.location.assign('/#')
      },
      (loginFailedError) => {
        //wyswietlic blad ze cos sie nie zgadza
        //np brak uzytkownika czy zle haslo
        setLoading(false)
        console.log('nie udalo sie zalogowac')
        console.log(loginFailedError)
      }
    )
  }

  const showAll = () => {
    console.log('is logged  = ' + logged)
    console.log('is Googlelogged  = ' + isGoogleLogged)
    console.log('takie cookie:')
    console.log(document.cookie)
  }

  const handleRegisterSubmit = (event) => {
    // podstawowe sprawdzanie typu: czy jest poprawy email wpisany EDIT: o przy submicie samo sprawdza
    // czy zgadzaja sie oba hasla
    // jezeli cos jest nietak to trzeba na czerwono wyswietlic komunikat

    setLoading(true)
    event.preventDefault()
    console.log('clicked register button')
    AuthService.register(login, email, password).then(
      () => {
        setLoading(false)
        console.log('udalo sie zarejestrowac')
        // wyswietlic komunikat ze sie udalo zarejestowac i mozna sie zalogowac
        // to mozna na zielono
        setIsRegistration(false)
      },
      (registerFailedError) => {
        // wyswietlic blad na czerwono ze cos nietak (np jest taki uzytkownik)
        setLoading(false)
        console.log('nie udalo sie zarejestrowac')
        console.log(registerFailedError)
      }
    )
  }

  return (
    <>
      <nav className='navbar'>
        <div className='nav-center'>
          <Link to='/'>
            <img src={logo} alt='hotpoll logo' className='logo' />
          </Link>
          <ul className='nav-links'>
            <li>
              <Link
                className={logged ? 'link-underline':'hidden'}
                to='/createpoll'
              >
                Create new poll
              </Link>
            </li>
            <li>
              <Link to='/' className='link-underline'>
                Home
              </Link>
            </li>
            <li>
              <Link to='/about' className='link-underline'>
                About
              </Link>
            </li>
            <li>
              <button
                onClick={logged ? logOut : openModal}
                className='link-underline'
              >
                {logged ? 'Logout' : 'Sign in'}
              </button>
            </li>
            <li>
              <button onClick={showAll}>debug</button>
            </li>
          </ul>
        </div>
      </nav>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        closeTimeoutMS={300}
        style={customStyles}
        contentLabel='Sign in Modal'
      >
        <h2>Sign in</h2>
        <Link to='#' className='link-underline' onClick={closeModal}>
          close
        </Link>

        <div className='login-or-create-account'>
          <h1>Login or Create Account for Free!</h1>
          <form
            onSubmit={isRegistration ? handleRegisterSubmit : handleLoginSubmit}
            className='loginForm'
          >
            <input
              className={isRegistration ? 'registration-form' : 'hidden'}
              type='email'
              placeholder='email'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type='text'
              placeholder='login'
              value={login}
              onChange={(e) => setLogin(e.target.value)}
            />
            <input
              type='password'
              placeholder='password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <input
              className={isRegistration ? 'registration-form' : 'hidden'}
              type='password'
              placeholder='confirm password'
              value={passwordConfirm}
              onChange={(e) => setPasswordConfirm(e.target.value)}
            />
            {loading ? (
              <Loading />
            ) : (
              <button type='submit'>
                {isRegistration ? 'Register' : 'Log in'}
              </button>
            )}
          </form>
          <h1>
            {isRegistration
              ? 'Already have an account?'
              : "Don't Have an Account?"}
          </h1>
          <div className='create-acc-or-guest'>
            <button
              id='register-btn'
              onClick={() => {
                setIsRegistration(!isRegistration)
              }}
            >
              {isRegistration ? 'Log in' : 'sign up'}
            </button>
            <button id='enter-as-guest-btn'>Enter as a Guest</button>

            <a className='google-btn' href={OAUTH_URL}>
              <button
                class='grid--cell s-btn s-btn__icon s-btn__google bar-md ba bc-black-100'
                data-provider='google'
                data-oauthserver='https://accounts.google.com/o/oauth2/auth'
                data-oauthversion='2.0'
              >
                <svg
                  aria-hidden='true'
                  class='native svg-icon iconGoogle'
                  width='18'
                  height='18'
                  viewBox='0 0 18 18'
                >
                  <path
                    d='M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 002.38-5.88c0-.57-.05-.66-.15-1.18z'
                    fill='#4285F4'
                  ></path>
                  <path
                    d='M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 01-7.18-2.54H1.83v2.07A8 8 0 008.98 17z'
                    fill='#34A853'
                  ></path>
                  <path
                    d='M4.5 10.52a4.8 4.8 0 010-3.04V5.41H1.83a8 8 0 000 7.18l2.67-2.07z'
                    fill='#FBBC05'
                  ></path>
                  <path
                    d='M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 001.83 5.4L4.5 7.49a4.77 4.77 0 014.48-3.3z'
                    fill='#EA4335'
                  ></path>
                </svg>
                Sign up with Google
              </button>
            </a>
          </div>
        </div>
      </Modal>
    </>
  )
}

export default Navbar
