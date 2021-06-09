import React, { useState, useContext, useEffect } from 'react';
import { useCallback } from 'react';

const url = 'http://localhost:4444/api/poll';
const AppContext = React.createContext();

const AppProvider = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [polls, setPolls] = useState([]);
  const [logged, setLogged] = useState(false);
  const [isGoogleLogged, setIsGoogleLogged] = useState(false);
  const [questions, setQuestions] = useState([
    {
      id: '0',
      text: 'pytanie checkbox',
      type: 'multiple',
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
    },
    {
      id: '1',
      text: 'pytanie radio button',
      type: 'radio',
      answers: [
        {
          id: '0',
          text: 'tak',
          votes: 0,
        },
        {
          id: '1',
          text: 'nie',
          votes: 0,
        },
      ],
    },
  ]);
  const [googleInfo, setGoogleInfo] = useState({});
  const [tags, setTags] = useState(['IT', 'FOOD']);
  const [searchTerm, setSearchTerm] = useState('');

  // const fetchPollByName = async ()=>
  // {
  //   const response = await fetch(`${search_url}${searchTerm}`)
  //   const data = await response.json()
  //   console.log(data)
  //   const { Polls } = data
  //   if (Polls)
  //   {
  //     const newPolls = Polls.map((item) => {
  //       const { idDrink, strDrink, strDrinkThumb, strAlcoholic, strGlass } =
  //         item

  //       return {
  //         id: idDrink,
  //         name: strDrink,
  //         image: strDrinkThumb,
  //         info: strAlcoholic,
  //         glass: strGlass,
  //       }
  //     })
  //     setCocktails(newCocktails)
  //   }
  // }

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
        // SZUKANIE ANKIET
        setSearchTerm,
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
