import React, { useState, useEffect } from "react";
import OptionsList from "./OptionsList";
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

  const removeAnswer = (id) => {
    //TODO
  };
  useEffect(() => {
    setAnswers(questions[parseInt(questionIndex)].answers);
    setIsMultiple(questions[parseInt(questionIndex)].type !== "radio");
  }, []);

  return (
    <div className="question-creator-wrapper">
      {alert.show && <Alert {...alert} removeAlert={showAlert} />}
      <div>
        <h3 className="poll-creator-heading">Add new question</h3>
        <input
          type="text"
          placeholder={"Question nr " + questionIndex}
          defaultValue={questions[parseInt(questionIndex)].text}
          onChange={(e) =>
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
            )
          }
        />
      </div>

      <div>
        <input
          type="checkbox"
          onChange={(e) => {
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
            <button>
              <FontAwesomeIcon icon={faTrash} className="fa-icon" />
            </button>
          </div>
        );
      })}

      <button
        onClick={() => {
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

            console.log(questions);
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
