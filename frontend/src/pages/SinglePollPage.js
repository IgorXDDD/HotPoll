import React, { useState, useEffect } from 'react';
import Loading from '../components/Loading';
import BarGraph from '../components/pollsUtilities/BarGraph';
import { useParams, Link } from 'react-router-dom';
import { useGlobalContext } from '../context';
import AuthService from '../services/user.service';

const url = 'http://localhost:4444/api/poll?pollID=';
const API_URL = 'http://localhost:4444/api/vote';
const completed_url = 'http://localhost:4444/api/vote?pollID=';
// Tu powinien byc link do endpointa zwracajacego pelne info o danej ankiecie
// Na razie zhardkodowalem to sobie

let tempPoll = {
  id: 2137,
  title: 'Pineapple and Pizza?',
  date: '16.04.2021',
  author: {
    id: 'Demongo',
    nickname: 'Demongo',
    email: null,
    password: null,
  },
  timesFilled: 38,
  tags: ['food', 'pineapple', 'pizza'],
  questions: [
    {
      id: 0,
      text: 'Does pineapple belong on pizza?',
      type: 'radio',
      answers: [
        {
          id: 0,
          text: 'Hell Yeah!',
        },
        {
          id: 1,
          text: 'Eww!',
        },
      ],
    },
    {
      id: 1,
      text: 'Checkbox test question',
      type: 'multiple',
      answers: [
        {
          id: 0,
          text: 'Option 1',
        },
        {
          id: 1,
          text: 'Option 2',
        },
        {
          id: 2,
          text: 'Option 3',
        },
      ],
    },
    {
      id: 2,
      text: 'Checkbox test question',
      type: 'radio',
      answers: [
        {
          id: 0,
          text: 'Option 1',
        },
        {
          id: 1,
          text: 'Option 2',
        },
        {
          id: 2,
          text: 'Option 3',
        },
      ],
    },
    {
      id: 3,
      text: 'inny checkbox',
      type: 'radio',
      answers: [
        {
          id: 0,
          text: 'Option 1',
        },
        {
          id: 1,
          text: 'Option 2',
        },
        {
          id: 2,
          text: 'Option 3',
        },
      ],
    },
  ],
};
const SinglePollPage = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState(false);
  const [poll, setPoll] = useState(tempPoll);
  const { isGoogleLogged } = useGlobalContext();
  const [alreadyCompleted, setAlreadyCompleted] = useState(false);
  //   const [formula,setFormula] = useState(
  //     {
  //       "pollID": id,
  //       "answers": [{"questionID":1,
  //     "answerID": 0}]
  // })
  let formula = [];
  let answersJson = [];
  const setAnswers = (qIndex, aIndex, isChecked, isRadio) => {
    if (isRadio) {
      formula[parseInt(qIndex)] = parseInt(aIndex);
    } else {
      if (Array.isArray(formula[parseInt(qIndex)])) {
      } else {
        formula[parseInt(qIndex)] = new Array();
      }
      if (isChecked) {
        formula[qIndex].push(parseInt(aIndex));
      } else {
        let found = formula[qIndex].indexOf(parseInt(aIndex));
        formula[parseInt(qIndex)].splice(found, 1);
      }
      formula[parseInt(qIndex)].sort();
    }
  };

  const sendPoll = () => {
    answersJson = [];
    formula.map((q, index) => {
      if (Array.isArray(q)) {
        q.map((qq) => {
          answersJson.push({ questionID: index, answerID: qq });
        });
      } else {
        answersJson.push({ questionID: index, answerID: q });
      }
    });

    let completedPoll = {
      pollID: id,
      answers: answersJson,
    };

    //WYSYLANIE DO API

    fetch(API_URL, {
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      mode: 'cors', // no-cors, *cors, same-origin
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, *same-origin, omit
      headers: {
        'Content-Type': 'application/json',
        Authorization: isGoogleLogged
          ? ''
          : `Bearer ${AuthService.getCurrentUser().jwt}`,
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      redirect: 'follow', // manual, *follow, error
      referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      body: JSON.stringify(completedPoll), // body data type must match "Content-Type" header
    });
    window.location.assign('/#');
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  // kiedy sie laduje pierwszy raz stronka z ankieta
  useEffect(() => {
    fetch(url + id)
      .then((response) => response.json())
      .then((data) => {
        setPoll(data);
        formula = data.questions.map(() => {
          return new Array();
        });
      })
      .catch((e) => {
        console.log('nie dostalismy ankiety :(');
      });

    fetch(`${completed_url}${id}`)
      .then((response) => response.text())
      .then((data) => {
        if (data == 'YES') {
          setAlreadyCompleted(true);
        }
        if (data == 'NO') {
          setAlreadyCompleted(false);
        }
      });
  }, [id]);

  if (loading) {
    return (
      <article className="poll-wrapper">
        <Link to="/" className="link-underline">
          ← Back
        </Link>
        <Loading />
      </article>
    );
  }
  if (!poll) {
    return (
      <article className="poll-wrapper">
        <Link to="/" className="link-underline">
          ← Back
        </Link>
        <h2 className="poll-title">No poll to display</h2>
      </article>
    );
  }

  const { title, author, date, timesFilled, tags, questions } = poll;
  let creationDate = new Date(date);
  return (
    <form className="poll-wrapper" onSubmit={handleSubmit}>
      <Link to="/" className="link-underline">
        ← Back
      </Link>
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

      {alreadyCompleted
        ? questions.map((question) => {
            return (
              <fieldset className="question-wrapper" key={question.id}>
                <legend className="question">
                  {`Q${parseInt(question.id) + 1}) `}
                  {question.text}
                </legend>

                {question.answers.map((answer) => {
                  return (
                    <>
                      <p className="question-answer" key={answer.id}>
                        {answer.text}
                      </p>

                      <BarGraph
                        aid={'aid' + question.id + answer.id}
                        inputPercentage={Math.round(
                          (answer.votes / timesFilled) * 100
                        )}
                      />
                    </>
                  );
                })}
              </fieldset>
            );
          })
        : questions.map((question) => {
            return (
              <fieldset className="question-wrapper" key={question.id}>
                <legend className="question">
                  {`Q${parseInt(question.id) + 1}) `}
                  {question.text}
                </legend>

                {question.answers.map((answer) => {
                  return (
                    <p className="question-answer" key={answer.id}>
                      <input
                        type={question.type === 'radio' ? 'radio' : 'checkbox'}
                        name={question.text}
                        id={question.id + '.' + answer.id}
                        // value={answer.text}
                        onChange={(e) => {
                          setAnswers(
                            question.id,
                            answer.id,
                            e.target.checked,
                            question.type === 'radio'
                          );
                        }}
                      />
                      <label for={question.id + '.' + answer.id}>
                        {answer.text}
                      </label>
                    </p>
                  );
                })}
              </fieldset>
            );
          })}

      {alreadyCompleted ? null : (
        <button
          className="poll-button"
          type="submit"
          onClick={() => sendPoll()}
        >
          Submit
        </button>
      )}

      <div className="poll-meta">
        <p>
          Poll completed: {timesFilled} time{timesFilled !== 1 ? 's' : null}
        </p>
        <div className="tags">
          {tags.map((tag, index) => {
            return tag ? (
              <p key={index}>
                <Link to={`/tag/${tag}`} className="tagLink">
                  <button>#{tag}</button>
                </Link>
              </p>
            ) : null;
          })}
        </div>
      </div>
    </form>
  );
};

export default SinglePollPage;
