import React, { useState, useEffect } from 'react';
import Poll from './pollsUtilities/Poll';
import Loading from './Loading';
import ReactPaginate from 'react-paginate';
import { useParams, Link } from 'react-router-dom';
import { useGlobalContext } from '../context';
import Welcome from '../pages/Welcome';
const poll_url = 'http://localhost:4444/api/poll?page=';
const poll_stat = 'http://localhost:4444/api/statistics';

const PollList = () => {
  const { id } = useParams();
  const { polls, setPolls, loading, setLoading, logged } = useGlobalContext();
  const [numberOfPages, setNumberOfPages] = useState(1);

  async function getPagesNumber() {
    fetch(poll_stat)
      .then((response) => response.text())
      .then((data) => {
        if (!isNaN(data)) {
          setNumberOfPages(Math.ceil(parseFloat(data) / 10));
        }
      });
  }

  useEffect(() => {
    window.scrollTo(0, 0);
    if (!logged) {
      setPolls([]);
    } else if (!id || parseInt(id) >= 0) {
      fetch(`${poll_url}${id ? parseInt(id) : 0}`)
        .then((response) => response.json())
        .then((data) => {
          // console.log('data: ', data)
          let polls = data;
          if (polls) {
            const newPolls = polls.map((poll) => {
              const { id, title, date, author, tags, questions, timesFilled } =
                poll;
              return {
                id,
                title,
                date,
                author,
                tags,
                questions,
                timesFilled,
              };
            });
            setPolls(newPolls);
            getPagesNumber();
          } else {
            setPolls([]);
          }
          setLoading(false);
        })
        .catch((e) => {
          console.log('error przy pobieraniu id strony z ankietami');
          console.log(e);
          // tutaj chyba jakies przejscie do strony z errorem co nie?
          // window.location.assign(`/#/error=${id}`)
          // setLoading(false);
        });
    } else {
      console.log('nie udalo sie pobrac ankiet');
      //WYSWIETL ERROR
      // window.location.assign(`/#/error=${id}`);
    }
  }, [id, logged]);

  const pageChange = (strona) => {
    // console.log(strona.selected)
    // console.log(window.location)
    // console.log("zamieniamy pollist na: ");
    // console.log(`/#/page/${(parseInt(strona.selected)).toString()}`)

    // TO SIE WYKONUJE NA SAMYM POCZATKU TEZ DLATEGO JEST STRONA GLOWNA
    // ZAWSZE PRZEKIEROWUJE NA /page/
    if (
      window.location.href !==
      `${window.location.origin}/#/page/${strona.selected}`
    ) {
      window.location.assign(`/#/page/${strona.selected}`);
    }
    // console.log('ZMIANA STRONY XD')
  };

  if (!logged) {
    return <Welcome />;
  }
  if (loading) {
    return (
      <section className="min-height-100vh">
        <Loading />
      </section>
    );
  } else if (polls.length < 1) {
    return (
      <section className="min-height-100vh">
        <div className="info">
          <h2>No polls found!</h2>
          <Link to="/" className="link-underline">
            Go to home page
          </Link>
        </div>
      </section>
    );
  }
  return (
    <>
      {polls.map((item) => {
        return <Poll key={item.id} {...item} />;
      })}
      {/* TO PLAN A */}
      {/* EDIT: DOBRA SMIGA AZ MILO TO REZYGNUJEMY Z PLANU B */}
      {/* TRZEBA ZMIENIC PAGECOUNT NA TAKI, KTORY JEST ZALEZNY OD LICZBY ANKIET CALKOWITEJ */}
      {/* tutaj zrodlo: https://github.com/AdeleD/react-paginate */}
      <ReactPaginate
        // classNames
        breakClassName={'breakClassName'}
        breakLinkClassName={'breakLinkClassName'}
        containerClassName={'containerClassName'}
        pageClassName={'pageClassName'}
        pageLinkClassName={'pageLinkClassName'}
        activeClassName={'activeClassName'}
        activeLinkClassName={'activeLinkClassName'}
        previousClassName={'previousClassName'}
        nextClassName={'nextClassName'}
        previousLinkClassName={'previousLinkClassName'}
        nextLinkClassName={'nextLinkClassName'}
        disabledClassName={'disabledClassName'}
        // the rest
        previousLabel={'previous'}
        nextLabel={'next'}
        breakLabel={'...'}
        pageCount={numberOfPages}
        marginPagesDisplayed={3}
        pageRangeDisplayed={3}
        onPageChange={pageChange}
        initialPage={id ? parseInt(id) : 0}
        previousLabel={<button>prev</button>}
        nextLabel={<button>next</button>}
      />
      {/* TO NASZ PLAN B 
      <Link
        className={!id || id == 1 ? 'hidden' : ''}
        to={`/page/${parseInt(id) - 1}`}
      >
        <button>previous page</button>
      </Link>

      <Link to={`/page/${id ? (parseInt(id) + 1).toString() : (2).toString()}`}>
        <button>next page</button>
      </Link> */}
    </>
  );
};

export default PollList;
