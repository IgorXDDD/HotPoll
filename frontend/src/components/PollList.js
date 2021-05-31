import React, {useState,useEffect} from "react";
import Poll from "./pollsUtilities/Poll";
import Loading from "./Loading";
import { useParams, Link } from 'react-router-dom'
import { useGlobalContext } from "../context";
import Welcome from "../pages/Welcome";
const poll_url = 'http://localhost:4444/api/poll?page='

const PollList = () => {
  const { id } = useParams()
  const { polls, setPolls, loading, setLoading, logged } = useGlobalContext();

  useEffect(() => 
  {
    if(!logged)
    {
      setPolls([]);
    }
    else if(!id || parseInt(id)>0)
    {
      fetch(`${poll_url}${id?(parseInt(id)-1):0}`)
        .then((response) => response.json())
        .then((data) => {
          // console.log('data: ', data)
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
          } 
          else 
          {
            setPolls([])
          }
          setLoading(false)
        })
        .catch((e) => {
          console.log("error przy pobieraniu id strony z ankietami");
          console.log(e);
          // tutaj chyba jakies przejscie do strony z errorem co nie?
          // window.location.assign(`/#/error=${id}`)
          // setLoading(false);
        })
    }
    else
    {
      //WYSWIETL ERROR
      window.location.assign(`/#/error=${id}`);
    }
  }, [id])


  if (!logged)
  {
    return <Welcome/>;
  }
  if (loading) {
    return <Loading />;
  } else if (polls.length < 1) {
    return (
      <div>
        <h2>No polls</h2>
        <Link to='/'>
          <button>Go to home page</button>
        </Link>

        {/* DODALEM MOZLIWOSC WROCENIA ZE STRONA W RAZIE JAK SIE ZAPEDZIMY ZA DALEKO */}
        <Link
          className={!id || id == 1 ? 'hidden' : ''}
          to={`/page/${parseInt(id) - 1}`}
        >
          <button>previous page</button>
        </Link>
      </div>
    )
  }
  return (
    <>
      {polls.map((item) => {
        return <Poll key={item.id} {...item} />
      })}

      <Link
        className={!id || id == 1 ? 'hidden' : ''}
        to={`/page/${parseInt(id) - 1}`}
      >
        <button>previous page</button>
      </Link>

      <Link to={`/page/${(id ? (parseInt(id) + 1).toString() : (2).toString())}`}>
        <button>next page</button>
      </Link>
    </>
  )
};

export default PollList;
