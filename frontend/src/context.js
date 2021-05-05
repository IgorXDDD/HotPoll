import React, { useState, useContext, useEffect } from "react";
import { useCallback } from "react";

const url = "http://localhost:4444/poll";
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [polls, setPolls] = useState([]);
  const [logged,setLogged] = useState(false);

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
    <AppContext.Provider value={{ loading, polls, logged, setLogged }}>
      {children}
    </AppContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
