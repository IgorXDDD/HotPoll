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
    {
      qid: 2,
      question: "Checkbox test question",
      type: "checkbox",
      answers: [
        {
          aid: 1,
          answer: "Option 1",
        },
        {
          aid: 2,
          answer: "Option 2",
        },
        {
          aid: 3,
          answer: "Option 3",
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

  const handleSubmit = (event) => {
    event.preventDefault();
  };

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
    <form className="poll-wrapper" onSubmit={handleSubmit}>
      <Link to="/">← Back</Link>
      <h1 className="poll-title">{title}</h1>
      <div className="poll-meta underline">
        <p>created: {date}</p>
        <p style={{ textAlign: "right" }}>author: {author}</p>
      </div>
      {questions.map((question) => {
        return (
          <fieldset className="question-wrapper" key={question.qid}>
            <legend className="question">
              {`Q${question.qid}) `}
              {question.question}
            </legend>
            {question.answers.map((answer) => {
              return (
                <p className="question-answer" key={answer.aid}>
                  <input
                    type={question.type === "radio" ? "radio" : "checkbox"}
                    name={question.question}
                    id={answer.aid}
                    value={answer.answer}
                  />
                  <label for={answer.aid}>{answer.answer}</label>
                </p>
              );
            })}
          </fieldset>
        );
      })}
      <button className="poll-button" type="submit">
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
    </form>
  );
};

export default SinglePoll;
