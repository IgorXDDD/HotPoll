import React, { useState, useContext, useEffect } from "react";
import { useCallback } from "react";

const url = "http://localhost:4444/poll";
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [polls, setPolls] = useState([]);
  const [logged,setLogged] = useState(false);
  const [questions, setQuestions] = useState([{
    "id": "1",
    'text': 'pytanko nr 1',
    'type': 'multiple',
    "answers": [
      {
        "id": "0",
        "text": "ans 1",
        "votes": 0
      },
      {
        "id": "1",
        "text": "ans 2",
        "votes": 0
      },
      {
        "id": "2",
        "text": "ans 3",
        "votes": 0
      },
      {
        "id": "3",
        "text": "ans 4",
        "votes": 0
      },
      {
        "id": "4",
        "text": "ans 5",
        "votes": 0
      }
    ]
  },
  {
    "id": "2",
    "text": "tak czy nie?",
    "type": "radio",
    "answers": [
      {
        "id": "1",
        "text": "tak",
        "votes": 2
      },
      {
        "id": "2",
        "text": "nie",
        "votes": 2
      }
    ]
  }])
  const [tags, setTags] = useState(["IT","FOOD"]);

  const fetchPolls = () => {
    setLoading(true);

    const newPolls = [
      {
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
      },
    ];
    setPolls(newPolls);
    setLoading(false);

    // To co wyzej jest do szybszego testowania na lokalnym serwerze localhost:3000
    // trzeba odkomentowac gore i zakomentowac dol

    // try {
    //   fetch(url)
    //     .then((response) => response.json())
    //     .then((data) => {
    //       console.log("data: ", data);
    //       let polls = [data];
    //       if (polls) {
    //         const newPolls = polls.map((poll) => {
    //           const {
    //             id,
    //             title,
    //             date,
    //             author,
    //             timesCompleted,
    //             tags,
    //             alreadyCompleted,
    //             questions,
    //           } = poll;
    //           return {
    //             id,
    //             title,
    //             date,
    //             author,
    //             timesCompleted,
    //             tags,
    //             alreadyCompleted,
    //             questions,
    //           };
    //         });
    //         setPolls(newPolls);
    //       } else {
    //         setPolls([]);
    //       }
    //       setLoading(false);
    //     });
    // } catch (error) {
    //   console.log(error);
    //   setLoading(false);
    // }
  };

  useEffect(() => {
    fetchPolls();
  }, []);

  return (
    <AppContext.Provider value={
      { loading,
        polls,
        logged,
        setLogged,
        questions,
        setQuestions,
        tags,
        setTags }
      }>
      {children}
    </AppContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };


//        JSONObject jo = new JSONObject()
//                .appendField("id",2137)
//                .appendField("title","Pineapple and Pizza?")
//                .appendField("date","16.04.2021")
//                .appendField("author","Demongo")
//                .appendField("timesCompleted",38)
//                .appendField("tags",new JSONArray()
//                        .appendElement("food")
//                        .appendElement("pineapple")
//                        .appendElement("pizza"))
//                .appendField("alreadyCompleted",false)
//                .appendField("questions",new JSONArray()
//                        .appendElement(new JSONObject()
//                        .appendField("qid",1)
//                        .appendField("question", "Does pineapple belong on pizza?")
//                        .appendField("type","radio")
//                        .appendField("answers", new JSONArray()
//                                .appendElement(new JSONObject()
//                                        .appendField("aid",1)
//                                        .appendField("answer","Hell Yeah!"))
//                                .appendElement(new JSONObject()
//                                        .appendField("aid",2)
//                                        .appendField("answer", "Eww!"))))