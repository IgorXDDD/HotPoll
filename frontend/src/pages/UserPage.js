import React, { useState, useEffect } from 'react'
import Poll from '../components/pollsUtilities/Poll'
import Loading from '../components/Loading'
import ReactPaginate from 'react-paginate'
import { useParams, Link } from 'react-router-dom'
import { useGlobalContext } from '../context'

const poll_url = 'http://localhost:4444/api/poll?username='
const PRINCIPAL_URL = 'http://localhost:4444/api/user/principal'
// const poll_stat = 'http://localhost:4444/api/statistics?tags='

const UserPage = () => {
  const { polls, setPolls, loading, setLoading, logged, isGoogleLogged,googleInfo } = useGlobalContext()
  const [userInfo, setUserInfo] = useState({
   username: '',
   email: ''
  })

  useEffect(() => {
    if (!isGoogleLogged) 
    {
     fetch(PRINCIPAL_URL)
       .then((response) => response.json())
       .then((json) => {
        console.log("DOSTAJEMY:");
        console.log(json);
         setUserInfo({
           email: json.principal.email,
           username: json.principal.username,
         })
       })
       .catch((e) => {
         console.log('nie logujemy googlem')
       })
    } 
    else 
    {
     setUserInfo(googleInfo);
    }

    
  }, [logged])

  useEffect(() => {
   window.scrollTo(0, 0)
   console.log('PRZED WYPISANIEM:')
   console.log(userInfo)
   if (!logged || userInfo.username == '') {
     setPolls([])
   } else {
     fetch(`${poll_url}${userInfo.username}`)
       .then((response) => response.json())
       .then((data) => {
         let polls = data
         if (polls) {
           const newPolls = polls.map((poll) => {
             const { id, title, date, author, tags, questions, timesFilled } =
               poll
             return {
               id,
               title,
               date,
               author,
               tags,
               questions,
               timesFilled,
             }
           })
           setPolls(newPolls)
         } else {
           setPolls([])
         }
         setLoading(false)
       })
       .catch((e) => {
         console.log('error przy pobieraniu id strony z ankietami')
         console.log(e)
         console.log('INFO:')
         console.log(userInfo)
         // window.location.assign(`/#/error=${userInfo.username}`)
         // tutaj chyba jakies przejscie do strony z errorem co nie?
         // window.location.assign(`/#/error=${id}`)
         // setLoading(false);
       })
   }
  }, [userInfo])

  if (!userInfo.username) {
    return (
      <>
        <h2>BAD PAGE</h2>
        <Link to='/#'>
          <button>go back</button>
        </Link>
      </>
    )
  }
  if (loading) {
    return <Loading />
  } else if (polls.length < 1) {
    return (
      <>
        <div className='info'>
          <h3>Username: {userInfo.username}</h3>
          <h3>User email: {userInfo.email}</h3>
        </div>
        <div className='info'>
          <h2>No polls for user: {userInfo.username}</h2>
          <Link to='/' className='link-underline'>
            Go to home page
          </Link>
        </div>
      </>
    )
  }
  return (
    <>
      <div className='info'>
        <h3>Username: {userInfo.username}</h3>
        <h3>User email: {userInfo.email}</h3>
      </div>

      <section className='min-height-100vh'>
        <div className='info'>
          <h2>Exploring {userInfo.username}'s polls</h2>
        </div>

        {polls.map((item) => {
          return <Poll key={item.id} {...item} />
        })}
      </section>
    </>
  )
}

export default UserPage