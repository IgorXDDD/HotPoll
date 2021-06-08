import React, { useState } from 'react';
import { useHistory, Link, Redirect } from 'react-router-dom';
import SingleQuestion from '../components/pollsUtilities/SingleQuestionCreator';
import { useGlobalContext } from '../context';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import Tag from '../components/pollsUtilities/Tags';
import AuthService from '../services/user.service.js';
const API_URL = 'http://localhost:4444/api/poll/';

const questionTemplate = {
  id: '',
  text: 'new question',
  type: 'radio',
  answers: [
    {
      id: '0',
      text: 'ans 1',
      votes: 0,
    },
    {
      id: '1',
      text: 'ans 2',
      votes: 0,
    },
  ],
};

function PollCreator() {
  const [pollName, setPollName] = useState('');

  let { logged, questions, tags, setQuestions, isGoogleLogged, googleInfo } =
    useGlobalContext();
  // const makeTen = () => {
  //   let ten = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
  //   let jwt = AuthService.getCurrentUser().jwt
  //   ten.map((one) => {
  //     fetch(API_URL, {
  //       method: 'POST', // *GET, POST, PUT, DELETE, etc.
  //       mode: 'cors', // no-cors, *cors, same-origin
  //       cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
  //       credentials: 'same-origin', // include, *same-origin, omit
  //       headers: {
  //         'Content-Type': 'application/json',
  //         Authorization: `Bearer ${jwt}`,
  //         // 'Content-Type': 'application/x-www-form-urlencoded',
  //       },
  //       redirect: 'follow', // manual, *follow, error
  //       referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
  //       body: JSON.stringify({
  //         id: null,
  //         date: new Date().toISOString(),
  //         author: {
  //           id: AuthService.getCurrentUser().username,
  //           nickname: AuthService.getCurrentUser().username,
  //           email: null,
  //           password: null,
  //         },
  //         title: one,
  //         questions: questions,
  //         tags: tags,
  //         timesFilled: 0,
  //       }), // body data type must match "Content-Type" header
  //     })
  //   })
  // }
  async function postData() {
    // console.log('kliknieto submit')
    // console.log('TAKA JEST ANKIETA')
    // console.log(questions)

    if (isGoogleLogged) {
      fetch(API_URL, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
          'Content-Type': 'application/json',
          // 'Authorization' : `Bearer ${jwt}`
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify({
          id: null,
          date: new Date().toISOString(),
          author: {
            id: googleInfo.username,
            nickname: googleInfo.username,
            email: googleInfo.email,
            password: null,
          },
          title: pollName,
          timesFilled: 0,
          questions: questions,
          tags: tags,
        }), // body data type must match "Content-Type" header
      });
    } else {
      let jwt = AuthService.getCurrentUser().jwt;
      // Default options are marked with *
      fetch(API_URL, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${jwt}`,
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify({
          id: null,
          date: new Date().toISOString(),
          author: {
            id: AuthService.getCurrentUser().username,
            nickname: AuthService.getCurrentUser().username,
            email: null,
            password: null,
          },
          title: pollName,
          questions: questions,
          tags: tags,
          timesFilled: 0,
        }), // body data type must match "Content-Type" header
      });
    }


    window.location.assign('/#/page/0');
    // window.location.href='/#'
    // window.location.reload();
    return false;
  }
  if (!logged) {
    return (
      <div className="info">
        <h1>Please Sign in!</h1>
      </div>
    );
  }
  return (
    <div className="poll-wrapper poll-creator-wrapper">
      {/* <button onClick={makeTen}>dodaj 11</button> */}
      <div className="poll-name-wrapper">
        <h1 className="poll-creator-heading">POLL CREATOR</h1>
        <input
          className="poll-name-input"
          type="text"
          required
          placeholder="enter poll name"
          value={pollName}
          onChange={(e) => setPollName(e.target.value)}
        />
      </div>

      {/* tutaj  wyswietlanie kazdego z pytan w ankiecie - jak na razie zostawilem domyslnie
             2 pytania */}
      {questions.map((question, index) => {
        return <SingleQuestion questionIndex={index} key={index} />;
      })}

      <button
        className="add-new-question-button"
        onClick={() => {
          let newQuestion = {};
          Object.assign(newQuestion, questionTemplate);
          newQuestion.id = questions.length.toString();
          setQuestions([...questions, newQuestion]);
        }}
      >
        <FontAwesomeIcon icon={faPlus} className="fa-icon" /> Add new Question
      </button>

      <Tag />

      <form
        className="submit-create-poll-form"
        action=""
        onSubmit={(e) => {
          // e.preventDefault()
          // console.log(questions)
          postData();
          window.setTimeout(() => {
            window.location.assign('/#/page/0');
          }, 50);
          // window.location.assign('/#/page/0')
        }}
      >
        <button className="submit-btn">Create</button>
      </form>

      {/* <button
        onClick={() => {
          console.log(questions)
        }}
      >
        DEBUG KLIK
      </button> */}

      {/* Przycisk do prostego debugowania tutaj wstawilem */}
    </div>
  );
}

export default PollCreator;
