import React, { useState } from "react";
import SingleQuestion from "./SingleQuestionCreator";
import { useGlobalContext } from "../../context";
import Tag from "./Tags";

function PollCreator() {
  const [pollName, setPollName] = useState("");
  let { questions, tags } = useGlobalContext();

  const handleSubmit = () => {
    // podwojnie wytabowane sa rzeczy, ktore po stronie serwera sie dodaja
    return {
      id: 2222,
      date: "05.05.2021",
      author: "Igor",
      title: pollName,
      timesCompleted: 0,
      tags: tags,
      questions: questions,
    };
  };

  return (
    <div className="poll-wrapper poll-creator-wrapper">
      <form
        className="set-poll-name"
        action=""
        onSubmit={() => {
          console.log(handleSubmit());
        }}
      >
        <button className="submit-poll-creator">Submit new poll</button>

        <h3 className="poll-creator-heading">Set Poll Name</h3>
        <input
          type="text"
          className="poll-name"
          placeholder="enter poll name"
          value={pollName}
          onChange={(e) => setPollName(e.target.value)}
        />
      </form>

      {/* tutaj  wyswietlanie kazdego z pytan w ankiecie - jak na razie zostawilem 2 pytania */}
      {questions.map((question, index) => {
        return <SingleQuestion questionIndex={index} key={index} />;
      })}
      <Tag />

      <br />
      <br />

      <button
        onClick={() => {
          console.log(questions[0]);
          console.log(tags);
        }}
      >
        DEBUG KLIK
      </button>
      {/* Przycisk do prostego debugowania tutaj wstawilem */}
    </div>
  );
}

export default PollCreator;
