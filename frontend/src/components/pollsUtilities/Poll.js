import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Poll = ({ id, title, date, author, tags, questions, timesFilled }) => {
  const [alreadyCompleted, setAlreadyCompleted] = useState(false);
  let creationDate = new Date(date);
  const completed_url = 'http://localhost:4444/api/vote?pollID=';

  useEffect(() => {
    fetch(`${completed_url}${id}`)
      .then((response) => response.text())
      .then((data) => {
        if (data === 'YES') {
          setAlreadyCompleted(true);
        }
        if (data === 'NO') {
          setAlreadyCompleted(false);
        }
      });
  }, []);

  return (
    <article className="poll-wrapper">
      <h1 className="poll-title">{title}</h1>
      <div className="poll-meta underline">
        <p>
          created:{' '}
          {creationDate.getDate() +
            '.' +
            (creationDate.getMonth() + 1) +
            '.' +
            creationDate.getFullYear() +
            '  ' +
            creationDate.getHours() +
            ':' +
            creationDate.getMinutes()}
        </p>
        <p style={{ textAlign: 'right' }}>author: {author.nickname}</p>
      </div>
      {questions.slice(0, 3).map((question) => {
        return (
          <section className="question-wrapper" key={question.id}>
            <h2 className="question question-simple">
              {`Q${parseInt(question.id) + 1}) `}
              {question.text}
            </h2>
          </section>
        );
      })}
      {questions.length > 3 ? (
        <h3>and {questions.length - 3} more...</h3>
      ) : null}
      <Link to={`/poll/${id}`} className="poll-link">
        <button className="poll-button">
          {alreadyCompleted ? 'See results' : 'Complete now'}
        </button>
      </Link>
      <div className="poll-meta">
        <p>
          Poll completed: {timesFilled} time{timesFilled !== 1 ? 's' : null}
        </p>
        <div className="tags">
          {tags.map((tag, index) => {
            return tag ? (
              <p key={index}>
                <Link to={`/tag/${tag}/0`} className="tagLink">
                  <button>#{tag}</button>
                </Link>
              </p>
            ) : null;
          })}
        </div>
      </div>
    </article>
  );
};

export default Poll;
