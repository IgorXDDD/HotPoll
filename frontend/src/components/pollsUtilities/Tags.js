import React, { useRef, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useGlobalContext } from "../../context";

function Tag() {
  const { tags, setTags } = useGlobalContext();
  const newTag = useRef("");
  
  const maxTagNumber = 3;
  let tagNumber;
  const removeTag = (tagID) => {
    setTags(
      tags.filter((tag) => {
        if (tag !== tagID) return tag;
      })
      );
      tagNumber-=1;
  };

  // const [tags,setTags] = useState(['food','IT']);
  return (
    <div>
      <h3 className="poll-creator-heading">Add Tags:</h3>
      <form
        action=""
        onSubmit={(e) =>
          {
            e.preventDefault();
          if (tags.includes(newTag.current.value.toUpperCase())) {
            console.log("Tag repetition: " + newTag.current.value);
          } 
          else 
          {
            tagNumber=tags.length
            if(tagNumber>=maxTagNumber)
            {
              console.log("too much tags");
            }
            else
            {
              tagNumber+=1;
              newTag.current.value && setTags([...tags, newTag.current.value.toUpperCase()]);
            }
          }
        }}
      >
        <input 
          type="text" 
          placeholder="add tag here" 
          ref={newTag} 
            />
        <button>
          <FontAwesomeIcon icon={faPlus} className="fa-icon" /> Add tag
        </button>
      </form>
      <ul>
        {tags.map((tag, index) => {
          return (
            <li key={index}>
              <button
                className="tag"
                onClick={() => {
                  removeTag(tag);
                }}
              >
                #{tag}
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

export default Tag;
