import React, { useState, useContext, useEffect } from "react";
import { useCallback } from "react";

const url = "http://localhost:4444/api/poll";
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [polls, setPolls] = useState([]);
  const [logged, setLogged] = useState(false);
  const [isGoogleLogged,setIsGoogleLogged] = useState(false);
  const [questions, setQuestions] = useState([
    {
      id: "0",
      text: "pytanie checkbox",
      type: "multiple",
      answers: [
        {
          id: "0",
          text: "ans 1",
          votes: 0,
        },
        {
          id: "1",
          text: "ans 2",
          votes: 0,
        }
      ],
    },
    {
      id: "1",
      text: "pytanie radio button",
      type: "radio",
      answers: [
        {
          id: "0",
          text: "tak",
          votes: 2,
        },
        {
          id: "1",
          text: "nie",
          votes: 2,
        },
      ],
    },
  ]);
  const [googleInfo, setGoogleInfo] = useState({});
  const [tags, setTags] = useState(["IT", "FOOD"]);

  // const fetchPolls = () => {
  //   setLoading(true);
  //   try {
  //     fetch(url)
  //       .then((response) => response.json())
  //       .then((data) => {
  //         console.log("data: ", data);
  //         let polls = data;
  //         if (polls) {
  //           const newPolls = polls.map((poll) => {
  //             const {
  //               id,
  //               title,
  //               date,
  //               author,
  //               tags,
  //               questions,
  //               timesFilled
  //             } = poll;
  //             return {
  //               id,
  //               title,
  //               date,
  //               author,
  //               tags,
  //               questions,
  //               timesFilled
  //             };
  //           });
  //           setPolls(newPolls);
  //         } else {
  //           setPolls([]);
  //         }
  //         setLoading(false);
  //       });
  //   } catch (error) {
  //     console.log(error);
  //     setLoading(false);
  //   }
  // };

  // useEffect(() => {
  //   fetchPolls();
  // }, []);

  return (
    <AppContext.Provider
      value={{
        loading,
        setLoading,
        //WYSWIETLANIE ANKIET
        polls,
        setPolls,
        //INFO O ZALOGOWANIU
        logged,
        setLogged,
        isGoogleLogged,
        setIsGoogleLogged,
        googleInfo,
        setGoogleInfo,
        //TWORZENIE ANKIET
        questions,
        setQuestions,
        tags,
        setTags,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
