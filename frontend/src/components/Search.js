import React, { useEffect, useState } from 'react';
import Loading from './Loading';
import Poll from './pollsUtilities/Poll';
// import { Link } from 'react-router-dom'
// import { useGlobalContext } from '../context'
// import Welcome from "../pages/Welcome"

const search_url = 'http://localhost:4444/api/poll?name=';
// const poll_stat = 'http://localhost:4444/api/statistics?name='
export const Search = (searchTerm) => {
  const [polls, setPolls] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // TUTAJ FETCH
    fetch(`${search_url}${searchTerm.searchTerm}`)
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
        } else {
          setPolls([]);
        }
        setLoading(false);
      });
  }, [searchTerm]);

  if (loading) {
    return (
      <section className="min-height-100vh">
        <Loading />;
      </section>
    );
  } else if (polls.length < 1) {
    return (
      <section className="min-height-100vh">
        <div className="info">
          <h2>No polls found!</h2>
          <h3>Change your search criteria</h3>
          {/* <Link to='/'>
           <button>Go to home page</button>
         </Link> */}
        </div>
      </section>
    );
  }
  return (
    <section className="min-height-100vh">
      {polls.map((item) => {
        return <Poll key={item.id} {...item} />;
      })}
    </section>
  );
};
