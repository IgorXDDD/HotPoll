import React, { useState } from "react";
import SingleQuestion  from "./SingleQuestionCreator"
import { useGlobalContext } from "../../context";
import Tag from "./Tags"
import AuthService from '../../services/user.service.js'
const API_URL = "http://localhost:4444/api/poll/";

const questionTemplate = 
{
    "id": 'f',
    "text": "new question",
    "type": "radio",
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
        }
    ]
}
let nowy = {};

function PollCreator() 
{
    const [pollName, setPollName] = useState('');
    let {questions,tags, setQuestions, isGoogleLogged} = useGlobalContext();

    async function postData() 
    {
        console.log(AuthService.getCurrentUser().jwt);
        console.log(AuthService.getCurrentUser().username);
        if(isGoogleLogged)
        {
                //wyslac trzeba geta na principal i stamtad pobrac info o uztkowniku
                // je potem ladnie zapakowac w posta wraz z ankietą
        }
        else
        {
            let jwt = AuthService.getCurrentUser().jwt;
            // Default options are marked with *
            const response = await fetch(API_URL, {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'same-origin', // include, *same-origin, omit
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${jwt}`
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify({
                "id": "997",
                "date": "2021-05-05T15:36:50.882",
                author: {
                    "id": AuthService.getCurrentUser().username,
                    "nickname": AuthService.getCurrentUser().username,
                    "email": null,
                    "password": null

                },
                title: pollName,
                timesCompleted: 0,
                questions: questions,
                tags: tags

            }) // body data type must match "Content-Type" header
            });
            return response.json(); // parses JSON response into native JavaScript objects
        }
      }
      

    const handleSubmit = ()=>
    {
        postData();
        // podwojnie wytabowane sa rzeczy, ktore po stronie serwera sie dodaja
        return{
                "id": "",
                "date": "2021-05-05T15:36:50.882",
                author: 'Igor',
            title: pollName,
            timesCompleted: 0,
            tags: tags,
            questions: questions
        }

    }

    return (
        <div className="poll-wrapper">
                <h3>Poll name</h3>
                <input 
                type="text" 
                required
                placeholder='enter poll name' 
                value={pollName}

                onChange={(e)=> setPollName(e.target.value)}
                />
                
            
            {/* tutaj  wyswietlanie kazdego z pytan w ankiecie - jak na razie zostawilem 2 pytania */}
            {questions.map((question,index)=>{
                return <SingleQuestion questionIndex={index} key={index}/>
            })}
            
            <button onClick={()=>{
                let newQuestion = {};
                Object.assign(newQuestion,questionTemplate);
                newQuestion.id = (questions.length+1).toString();
                setQuestions([...questions,newQuestion]);
            }}>
                Add new Question
            </button>
            <br />
            <br />
            <Tag/>
            <br />
            <h2>Submit Poll</h2>
            <form action="" onSubmit={(e)=>{
                    e.preventDefault();
                    console.log(questions);
                    // handleSubmit();
                }}>
                <button className='submit-btn' >
                    Submit new poll
                </button>
            </form>

            {/* <button onClick={
                ()=>{
                    console.log(questions[questions.length-1]);
                }
            }>
                DEBUG KLIK
            </button>
         */}
            {/* Przycisk do prostego debugowania tutaj wstawilem */}
        </div>
    )
}

export default PollCreator
