import React,{ useRef } from 'react'
import { useGlobalContext } from "../../context";

function Tag() {
    

    const {tags,setTags} = useGlobalContext();
    const newTag = useRef("");
    const removeTag = (tagID) =>
    {
        setTags(tags.filter((tag)=>{
            if(tag!==tagID)
                return tag
        }
        ))
    }

    // const [tags,setTags] = useState(['food','IT']);

    return (
        <div>
            <h3>Add Tags:</h3>
            <form action="" onSubmit={()=>
            {
                if(tags.includes(newTag.current.value.toUpperCase()))
                {
                    console.log("JUZ TAKI TAG JEST:" + newTag.current.value );
                }
                else
                {
                    setTags([...tags,newTag.current.value.toUpperCase()])
                }
            }}>
                <input type="text" placeholder="add tag here" ref={newTag} />
                <button>Add tag</button>
            </form>
            <ul>
                {
                    tags.map((tag, index)=>{
                        return <li key={index}>
                            <button onClick={()=>{removeTag(tag)}}>
                                #{tag}
                            </button>
                        </li>
                    })
                }
            </ul>
        </div>
    )
}

export default Tag
