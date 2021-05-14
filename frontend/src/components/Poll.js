import React from "react";
import { Link } from "react-router-dom";

const Poll = ({ id, title, date, author, tags, questions }) => {
  // w domyśle te dwie wartości powinny przychodzić
  // z ankiety albo skądś indziej
  let timesCompleted = 10;
  let alreadyCompleted = false;

  return (
    <article className="poll-wrapper">
      <h1 className="poll-title">{title}</h1>
      <div className="poll-meta underline">
        <p>created: {date}</p>
        <p style={{ textAlign: "right" }}>author: {author.nickname}</p>
      </div>
      {questions.slice(0, 3).map((question) => {
        return (
          <section className="question-wrapper" key={question.id}>
            <h2 className="question question-simple">
              {`Q${question.id}) `}
              {question.text}
            </h2>
            {/* // comment vvv */}
            {/* {question.answers.map((answer) => {
              return (
                <p className="question-answer" key={answer.id}>
                  {answer.text}
                </p>
              );
            })} */}
            {/* // comment ^^^ */}
            {questions.length > 3 ? <h2>and more...</h2> : null}
          </section>
        );
      })}
      <Link to={`/poll/${id}`} className="poll-link">
        <button className="poll-button">
          {alreadyCompleted ? "See results" : "Complete now"}
        </button>
      </Link>
      <div className="poll-meta">
        <p>Poll completed: {timesCompleted} times</p>
        <div className="tags">
          {tags.map((tag, index) => {
            return tag ? <p key={index}>#{tag}</p> : null;
          })}
        </div>
      </div>
    </article>
  );
};

export default Poll;
