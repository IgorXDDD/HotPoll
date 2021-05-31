import React, { useState, useEffect } from 'react'
import Poll from './pollsUtilities/Poll'
import Loading from './Loading'
import ReactPaginate from 'react-paginate'
import { useParams, Link } from 'react-router-dom'
import { useGlobalContext } from '../context'
const poll_url = 'http://localhost:4444/api/poll?tags='

const TagList = () => {
  const { id,page } = useParams()
  const { polls, setPolls, loading, setLoading, logged } = useGlobalContext()

  useEffect(() => {
    console.log('probojemy pobrac ankiety')
    if(isNaN(page))
    {
     console.log("zly adres :((");
    }
    else if (!logged) {
      setPolls([])
    } 
    else 
    {
      console.log('ID TAKIE: ' + id)
      fetch(`${poll_url}${id}&page=${page?(parseInt(page)):0}`)
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
            console.log('udalo sie zdobyc takie:')
            console.log(newPolls)
          } else {
            setPolls([])
          }
          setLoading(false)
        })
        .catch((e) => {
          console.log('error przy pobieraniu id strony z ankietami')
          console.log(e)
          window.location.assign(`/#/error=${id}`)
          // tutaj chyba jakies przejscie do strony z errorem co nie?
          // window.location.assign(`/#/error=${id}`)
          // setLoading(false);
        })
    }
  }, [id, page, logged])

  // if (!logged) {
  //   return <div><h1>Please Sign in!</h1></div>
  // }
  // const dupa = (strona)=>
  // {
  //  return `${window.location.origin}/#/tag/${id}/${strona.selected}`
  // }

  const pageChange = (strona)=>
  {
   // console.log(strona.selected);
   // console.log(window.location);
   // console.log("zamieniamy taglist na:");
   // console.log(`/#/tag/${id}/${strona.selected}`)
   window.location.assign(`/#/tag/${id}/${strona.selected}`)

  }

  if(page && isNaN(page))
  {
    return <>
    <h2>BAD PAGE</h2>
    <Link to="/#">
    <button>
     go back
    </button>
    </Link>
    </>
  }
  if (loading) {
    return <Loading />
  } else if (polls.length < 1) {
    return (
      <div>
        <h2>No polls with such tag: {id} or You've exceeded our page limit (we're still working on that)</h2>
        <h2>Oraz {page}</h2>
        <Link to='/'>
          <button>Go to home page</button>
        </Link>
      </div>
    )
  }
  return (
    <>
      <h2>Exploring {id} polls:</h2>
      {polls.map((item) => {
        return <Poll key={item.id} {...item} />
      })}
      {/* TUTAJ TRZEBA UZUPELNIC STYLE ORAZ SZTYWNO WPISANE WARTOSCI NA TO, CO OTRZYMUJEMY Z API */}
      <ReactPaginate
        previousLabel={'previous'}
        nextLabel={'next'}
        breakLabel={'...'}
        breakClassName={'break-me'}
        pageCount={10}
        marginPagesDisplayed={3}
        pageRangeDisplayed={3}
        onPageChange={pageChange}
        containerClassName={'pagination'}
        activeClassName={'active'}
        initialPage={page ? parseInt(page) : 0}
        previousLabel={<button>previous</button>}
        nextLabel={<button>next</button>}
      />
    </>
  )
}

export default TagList
