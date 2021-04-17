import React, { useState, useEffect } from "react";
import Loading from "../components/Loading";
import { useParams, Link } from "react-router-dom";
import { useGlobalContext } from "../context";

// const url = "http://localhost:4444/poll/id="
// Tu powinien byc link do endpointa zwracajacego pelne info o danej ankiecie
// Na razie zhardkodowalem to sobie

const poll = {
  id: 2137,
  title: "Pineapple and Pizza?",
  date: "16.04.2021",
  author: "Demongo",
  timesCompleted: 38,
  tags: ["food", "pineapple", "pizza"],
  alreadyCompleted: false,
  questions: [
    {
      qid: 1,
      question: "Does pineapple belong on pizza?",
      type: "radio",
      answers: [
        {
          aid: 1,
          answer: "Hell Yeah!",
        },
        {
          aid: 2,
          answer: "Eww!",
        },
      ],
    },
  ],
};

const SinglePoll = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState(false);
  // const [poll2, setPoll2] = useState(null);

  // useEffect(() => {
  //     setLoading(true);
  //     const getPoll = async () => {
  //         try {
  //             const getPoll = await fetch(`${url}${id}`);
  //             const data = await Response.json();
  //             if(data){
  //              setPoll2(data);
  //             }
  //             else {
  //                 setPoll2(null);
  //             }
  //             setLoading(false);

  //         } catch (error) {
  //             console.log(error);
  //             setLoading(false);
  //         }

  //     };
  //     getPoll();
  // }, [id]);

  if (loading) {
    return (
      <article className="poll-wrapper">
        <Link to="/">← Back</Link>
        <Loading />
      </article>
    );
  }
  if (!poll) {
    return (
      <article className="poll-wrapper">
        <Link to="/">← Back</Link>
        <h2 className="poll-title">No poll to display</h2>
      </article>
    );
  }

  const {
    title,
    author,
    date,
    timesCompleted,
    tags,
    questions,
    alreadyCompleted,
  } = poll;

  return (
    <article className="poll-wrapper">
      <Link to="/">← Back</Link>
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
            {questions.length > 3 ? <h2>and more...</h2> : null}
          </section>
        );
      })}
      <button className="poll-button">
        {alreadyCompleted ? "See results" : "Submit"}
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

export default SinglePoll;
