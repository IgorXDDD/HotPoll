import React, { useState, useRef } from "react";
import OptionsList from './OptionsList';
import Alert from './Alert';

function PollCreator() 
{
    const [polls,setPolls] = useState({});
    const [list, setList] = useState([{id: 1,title: 'opcja nr 1'},{id: 3,title: 'opcja nr 3'},{id: 2,title: 'opcja nr 2'}]);
    const [newOption, setNewOption] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [editID, setEditID] = useState(null);
    const [alert, setAlert] = useState({ show: false, msg: '', type: '' });
    const [tags,setTags] = useState(['food','IT']);
    const [pollName, setPollName] = useState('');
    const [isMultiple,setIsMultiple] = useState(false);
    const [newTag, setNewTag] = useState('');

    const addOption = (e) => {
        e.preventDefault();
        if (!newOption) {
            showAlert(true, 'danger', 'please enter value');
          } else if (newOption && isEditing) {
          setList(
            list.map((item) => {
              if (item.id === editID) {
                return { ...item, title: newOption };
              }
              return item;
            })
          );
          setNewOption('');
          setEditID(null);
          setIsEditing(false);
          showAlert(true, 'success', 'value changed');
        } else {
            showAlert(true, 'success', 'item added to the list');   
            const newItem = { id: new Date().getTime().toString(), title: newOption };
        
            setList([...list, newItem]);
            setNewOption('');
        }
      };
    const removeOption = (id)=>
    {
        showAlert(true, 'danger', 'item removed');
        setList(list.filter((item)=> item.id !==id));
    }

    const editItem = (id) => 
    {
        const specificItem = list.find((item) => item.id === id);
        setIsEditing(true);
        setEditID(id);
        setNewOption(specificItem.title);
      };

    const clearList = () => 
    {
        showAlert(true, 'danger', 'empty list');
        setList([]);
    }; 

    const handleSubmit = ()=>
    {
        return{
            id: 2222,
            date: "05.05.2021",
            author: 'Igor',
            timesCompleted: 0,
            tags: tags.map((tag)=>{return tag}),
            question: {pollName},
            answers: list.map((item)=>{return item})
        }
    }

    const showAlert = (show = false, type = '', msg = '') => {
    setAlert({ show, type, msg });

    };
    return (
        <div>
            <form action="" onSubmit={()=>{
                    console.log(handleSubmit());
                }}>
                <button className='submit-btn' >
                    Submit new poll
                </button>
            
                <h3>Poll name</h3>
                <input 
                type="text" 
                placeholder='enter poll name' 
                value={pollName}
                onChange={(e)=> setPollName(e.target.value)}
                />
            </form>
            <h2>Add poll options</h2>
            <form action="" onSubmit={addOption}>
            {alert.show && <Alert {...alert} removeAlert={showAlert} list={list} />}
                <input
                type='text'
                placeholder='e.g. eggs'
                value={newOption}
                onChange={(e) => setNewOption(e.target.value)}
                />
                <button type='submit' className='submit-btn'>
                    {isEditing ? 'edit' : 'Add new option'}
                </button>
                <div>
                    <input type="checkbox" onChange={(e)=> setIsMultiple(e.target.checked) }/>
                    <label htmlFor="multiple">Allow multiple choice</label>
                </div>
            </form>
            {list.length > 0 && (
        <div className='grocery-container'>
          <OptionsList items={list} removeItem={removeOption} editItem={editItem} />
        
          <button className='clear-btn' onClick={clearList}>
            clear items
          </button>
        </div>
      )}
        <form action="" onSubmit={()=>{setTags(...tags,newTag)
             setNewTag('')}}>
            <input type="text" name="" value={newTag} placeholder="add tag here" onChange={(e)=>setNewTag(e.target.value)}/>
            <button>add tag</button>
        </form>

        <h3>tags:</h3>
        <ul>
            {tags.map((tag)=>
            {
                return(
                    <li>#{tag}</li>
                )
            })}
        </ul>
        </div>
    )
}

export default PollCreator
