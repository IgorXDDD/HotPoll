import React, { useState, useEffect, useRef } from "react";
import { useGlobalContext } from "../../context";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPlus } from "@fortawesome/free-solid-svg-icons";
import Alert from "./Alert";

function SingleQuestionCreator({ questionIndex }) {
  const [answers, setAnswers] = useState([]);
  const [alert, setAlert] = useState({ show: false, msg: "", type: "" });
  const [isMultiple, setIsMultiple] = useState(false);
  const { questions, setQuestions } = useGlobalContext();

  const showAlert = (show = false, type = "", msg = "") => {
    setAlert({ show, type, msg });
  };

  let questionName = useRef("question 2");

  const removeQuestion = () => 
  {
    let newArray = []
    Object.assign(newArray,questions);

    newArray.splice(questionIndex,1);

    newArray = newArray.map((question)=>
    {
      if(question.id<(questionIndex+1))
      {
        return question;
      }
      else
      {
        let newQuestion = {};
        Object.assign(newQuestion,question);
        newQuestion.id = (newQuestion.id-1).toString();
        return newQuestion
      }
    })

    setQuestions(newArray);
  };

  const removeAnswer = (index) =>
  {
    if(questions[questionIndex].answers.length<3)
    {
        console.log("Cannot leave less than 2 answers!");
    }
    else
    {
      let newArray = []
      Object.assign(newArray,questions[questionIndex].answers);
      newArray.splice(index,1);
      newArray = newArray.map((answer)=>{
        if(answer.id<(index+1))
        {
          return answer;
        }
        else
        {
          let newAnswer = {};
          Object.assign(newAnswer,answer);
          newAnswer.id=(newAnswer.id-1).toString();
          return newAnswer;
        }
      })

      setQuestions(questions.map((question,qIndex)=>
      {
        if(qIndex!== questionIndex)
        {
          return question;
        }
        else
        {
          let modified = {};
          Object.assign(modified,questions[qIndex]);
          modified.answers=newArray;
          return modified;
        }
      }));
    }
  }

  useEffect(() => {
    setAnswers(questions[parseInt(questionIndex)].answers);
    setIsMultiple(questions[parseInt(questionIndex)].type !== "radio");
  }, [questions]);

  return (
    <div className="question-creator-wrapper">
      {alert.show && <Alert {...alert} removeAlert={showAlert} />}
  
      <div>
        <h3 className="poll-creator-heading">Add new question</h3>
        <button onClick={removeQuestion}>
          Remove question
        </button>
        <br/>
        <input
          type="text"
          placeholder={"Question nr " + (questionIndex+1)}
          required
          defaultValue={questions[parseInt(questionIndex)].text}
          ref={questionName}
          onChange={(e) =>
            {
              console.log("TAKIE JEST: "+questionName.current.value);
            e.preventDefault();
            setQuestions(
              questions.map((question) => {
                if (questions.indexOf(question) != questionIndex) {
                  return question;
                } else {
                  let tmp = question;
                  tmp.text = questionName.current.value
                  return tmp;
                }
              })
            )
          }
          }
        />
      </div>

      <div>
        <input
          type="checkbox"
          onChange={(e) => {
            e.preventDefault();
            setIsMultiple(e.target.checked);
            setQuestions(
              questions.map((question, qIndex) => {
                if (qIndex !== questionIndex) {
                  return question;
                } else {
                  let tmp = question;
                  tmp.type = isMultiple ? "radio" : "multiple";
                  return tmp;
                }
              })
            );
          }}
          checked={isMultiple}
        />
        <label htmlFor="multiple" className="allow-multiple-checkbox">
          Allow multiple choice
        </label>
      </div>

      {questions[parseInt(questionIndex)].answers.map((ans, index) => {
        return (
          <div key={index}>
            Answer nr {index + 1}:
            <input
              type="text"
              required
              value={ans.text}
              placeholder="new answer"
              onChange={(e) => {
                e.preventDefault();
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
                            votes: ans.votes,
                          };
                        else return ans2;
                      });
                      return tmp;
                    }
                  })
                );
              }}
            />
            <button onClick={()=>{
              removeAnswer(index);
            }}>
              <FontAwesomeIcon icon={faTrash} className="fa-icon" />
            </button>
          </div>
        );
      })}

      <button
        onClick={(e) => {
          e.preventDefault();
          if (questions[parseInt(questionIndex)].answers.length < 10) {
            setAnswers([...answers, { id: answers.length + 1, text: "" }]);

            setQuestions(
              questions.map((question, qIndex) => {
                if (qIndex !== questionIndex) {
                  return question;
                } else {
                  let tmp = question;
                  // tmp.answers[index]={id: tmp.answers.length.toString() ,text: e.target.value, votes: 0};
                  tmp.answers = [
                    ...tmp.answers,
                    { id: answers.length.toString(), text: "", voted: 0 },
                  ];

                  return tmp;
                }
              })
            );
          } else showAlert(true, "danger", "Too many answers!");
        }}
      >
        <FontAwesomeIcon icon={faPlus} className="fa-icon" /> Add Answer
      </button>
      {/* <OptionsList items={answers} removeItem={removeAnswer} editItem = {editAnswer}/> */}
    </div>
  );
}

export default SingleQuestionCreator;
