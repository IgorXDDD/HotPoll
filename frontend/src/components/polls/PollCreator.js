import React, { useState } from "react";
import SingleQuestion  from "./SingleQuestionCreator"
import { useGlobalContext } from "../../context";
import Tag from "./Tags"
import AuthService from '../../services/user.service.js'
const API_URL = "http://localhost:4444/api/poll/";

const questionTemplate = 
{
    "id": '',
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


function PollCreator() 
{
    const [pollName, setPollName] = useState('');
    let {questions,tags, setQuestions, isGoogleLogged, googleInfo} = useGlobalContext();

    async function postData() 
    {
        console.log("kliknieto submit");
        console.log("TAKA JEST ANKIETA");
        console.log(questions);
       
        if(isGoogleLogged)
        {
            const response = await fetch(API_URL, {
                method: 'POST', // *GET, POST, PUT, DELETE, etc.
                mode: 'cors', // no-cors, *cors, same-origin
                cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                credentials: 'same-origin', // include, *same-origin, omit
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization' : `Bearer ${jwt}`
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: 'follow', // manual, *follow, error
                referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                body: JSON.stringify({
                    "id": "0",
                    "date": new Date().toISOString(),
                    author: {
                        "id": googleInfo.username,
                        "nickname": googleInfo.username,
                        "email": googleInfo.email,
                        "password": null
                    },
                    title: pollName,
                    timesCompleted: 0,
                    questions: questions,
                    tags: tags
    
                }) // body data type must match "Content-Type" header
                });
        }
        else
        {
            console.log("nie google");
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
                "id": "0",
                "date": new Date().toISOString(),
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
                
            
            {/* tutaj  wyswietlanie kazdego z pytan w ankiecie - jak na razie zostawilem domyslnie
             2 pytania */}
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
                    postData();
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
