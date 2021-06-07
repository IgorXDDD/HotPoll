import React, { useState, useEffect, useRef } from 'react';
import { useGlobalContext } from '../../context';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faPlus } from '@fortawesome/free-solid-svg-icons';
import Alert from './Alert';

function SingleQuestionCreator({ questionIndex }) {
  const [answers, setAnswers] = useState([]);
  const [alert, setAlert] = useState({ show: false, msg: '', type: '' });
  const [isMultiple, setIsMultiple] = useState(false);
  const [questionName, setQuestionName] = useState('');
  const { questions, setQuestions } = useGlobalContext();

  const showAlert = (show = false, type = '', msg = '') => {
    setAlert({ show, type, msg });
  };

  const removeQuestion = () => {
    let newArray = [];
    Object.assign(newArray, questions);
    newArray.splice(questionIndex, 1);
    newArray = newArray.map((question) => {
      if (question.id < questionIndex + 1) {
        return question;
      } else {
        let newQuestion = {};
        Object.assign(newQuestion, question);
        newQuestion.id = (newQuestion.id - 1).toString();
        return newQuestion;
      }
    });

    setQuestions(newArray);
  };

  const removeAnswer = (index) => {
    if (questions[questionIndex].answers.length < 3) {
      console.log('Cannot leave less than 2 answers!');
    } else {
      let newArray = [];
      Object.assign(newArray, questions[questionIndex].answers);
      newArray.splice(index, 1);
      newArray = newArray.map((answer) => {
        if (answer.id < index + 1) {
          return answer;
        } else {
          let newAnswer = {};
          Object.assign(newAnswer, answer);
          newAnswer.id = (newAnswer.id - 1).toString();
          return newAnswer;
        }
      });

      setQuestions(
        questions.map((question, qIndex) => {
          if (qIndex !== questionIndex) {
            return question;
          } else {
            let modified = {};
            Object.assign(modified, questions[qIndex]);
            modified.answers = newArray;
            return modified;
          }
        })
      );
    }
  };

  useEffect(() => {
    setAnswers(questions[parseInt(questionIndex)].answers);
    setIsMultiple(questions[parseInt(questionIndex)].type === 'multiple');
  }, [questions]);

  useEffect(() => {
    setQuestionName(questions[parseInt(questionIndex)].text);
  }, []);

  return (
    <div className="question-creator-wrapper">
      {alert.show && <Alert {...alert} removeAlert={showAlert} />}

      <div className="single-question-top">
        <div className="remove-question-button" onClick={removeQuestion}>
          X
        </div>
        <h3 className="poll-creator-question-heading">
          Question #{questionIndex + 1}
        </h3>
        <input
          type="text"
          className="single-question-text"
          placeholder={'Question nr ' + (questionIndex + 1)}
          required
          defaultValue={questions[parseInt(questionIndex)].text}
          value={questions[parseInt(questionIndex)].text}
          onChange={(e) => {
            setQuestions(
              questions.map((question) => {
                if (questions.indexOf(question) != questionIndex) {
                  return question;
                } else {
                  let tmp = question;
                  tmp.text = e.target.value;
                  return tmp;
                }
              })
            );
          }}
        />
      </div>

      {questions[parseInt(questionIndex)].answers.map((ans, index) => {
        return (
          <div key={index} className="single-answer-wrapper">
            <p className="answer-nr-text">Answer #{index + 1}</p>
            <input
              type="text"
              className="single-answer-input"
              required
              value={ans.text}
              placeholder="new answer"
              onChange={(e) => {
                e.preventDefault();
                console.log(questionName);
                setQuestions(
                  questions.map((question, qIndex) => {
                    if (qIndex !== questionIndex) {
                      return question;
                    } else {
                      let tmp = question;
                      // tmp.answers[index]={id: tmp.answers.length.toString() ,text: e.target.value, votes: 0};
                      tmp.answers = tmp.answers.map((ans2) => {
                        if (ans2.id === ans.id)
                          return {
                            id: ans.id.toString(),
                            text: e.target.value,
                            votes: ans.votes ? ans.votes : 0,
                          };
                        else return ans2;
                      });
                      return tmp;
                    }
                  })
                );
              }}
            />
            <button
              onClick={() => {
                removeAnswer(index);
              }}
            >
              <FontAwesomeIcon icon={faTrash} className="fa-icon" />
            </button>
          </div>
        );
      })}

      <div className="single-answer-bottom">
        <button
          className="add-answer-button"
          onClick={(e) => {
            e.preventDefault();
            if (questions[parseInt(questionIndex)].answers.length < 10) {
              setAnswers([...answers, { id: answers.length + 1, text: '' }]);

              setQuestions(
                questions.map((question, qIndex) => {
                  if (qIndex !== questionIndex) {
                    return question;
                  } else {
                    let tmp = question;
                    // tmp.answers[index]={id: tmp.answers.length.toString() ,text: e.target.value, votes: 0};
                    tmp.answers = [
                      ...tmp.answers,
                      { id: answers.length.toString(), text: '', voted: 0 },
                    ];

                    return tmp;
                  }
                })
              );
            } else showAlert(true, 'danger', 'Too many answers!');
          }}
        >
          <FontAwesomeIcon icon={faPlus} className="fa-icon" /> Add Answer
        </button>

        <div className="checkbox-wrapper">
          <input
            type="checkbox"
            name="isRadio"
            id={questionIndex}
            checked={isMultiple}
            onClick={(e) => {
              setIsMultiple(!isMultiple);
              setQuestions(
                questions.map((question, qIndex) => {
                  if (qIndex !== questionIndex) {
                    return question;
                  } else {
                    question.type = e.target.checked ? 'multiple' : 'radio';
                    return question;
                  }
                })
              );
            }}
          />

          <label for={questionIndex} className="allow-multiple-checkbox">
            Allow multiple choice
          </label>
        </div>
      </div>
      {/* <OptionsList items={answers} removeItem={removeAnswer} editItem = {editAnswer}/> */}
    </div>
  );
}

export default SingleQuestionCreator;
