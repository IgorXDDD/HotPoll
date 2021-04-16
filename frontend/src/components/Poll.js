import React from "react";

const Poll = ({
  id,
  title,
  date,
  author,
  timesCompleted,
  tags,
  alreadyCompleted,
  questions,
}) => {
  return (
    <article className="poll-wrapper">
      <h1 className="poll-title">{title}</h1>
      <div className="poll-meta underline">
        <p>created: {date}</p>
        <p style={{ textAlign: "right" }}>author: {author}</p>
      </div>
      {questions.map((question) => {
        return (
          <section className="question-wrapper" key={question.qid}>
            <h2 className="question">
              {`Q${question.qid}) `}
              {question.question}
            </h2>
            {question.answers.map((answer) => {
              return (
                <p className="question-answer" key={answer.aid}>
                  {answer.answer}
                </p>
              );
            })}
          </section>
        );
      })}
      <button className="poll-button">
        {alreadyCompleted ? "See results" : "Complete now"}
      </button>
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
