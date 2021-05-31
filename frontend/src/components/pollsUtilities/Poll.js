import React from "react";
import { Link } from "react-router-dom";

const Poll = ({ id, title, date, author, tags, questions, timesFilled }) => {
  // w domyśle te dwie wartości powinny przychodzić
  // z ankiety albo skądś indziej
  let alreadyCompleted = false;
  let creationDate = new Date(date);

  return (
    <article className='poll-wrapper'>
      <h1 className='poll-title'>{title}</h1>
      <div className='poll-meta underline'>
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
          <section className='question-wrapper' key={question.id}>
            <h2 className='question question-simple'>
              {`Q${(parseInt(question.id)+1)}) `}
              {question.text}
            </h2>
            {questions.length > 3 ? <h2>and more...</h2> : null}
          </section>
        )
      })}
      <Link to={`/poll/${id}`} className='poll-link'>
        <button className='poll-button'>
          {alreadyCompleted ? 'See results' : 'Complete now'}
        </button>
      </Link>
      <div className='poll-meta'>
        <p>Poll completed: {timesFilled} times</p>
        <div className='tags'>
          {tags.map((tag, index) => {
            return tag ? (
              <p key={index}>
                <Link to={`/tag/${tag}`} className='tagLink'>
                  <button>#{tag}</button>
                </Link>
              </p>
            ) : null
          })}
        </div>
      </div>
    </article>
  )
};

export default Poll;
