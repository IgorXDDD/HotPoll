import React, { useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useGlobalContext } from "../../context";

function Tag() {
  const { tags, setTags } = useGlobalContext();
  const newTag = useRef("");
  const removeTag = (tagID) => {
    setTags(
      tags.filter((tag) => {
        if (tag !== tagID) return tag;
      })
    );
  };

  // const [tags,setTags] = useState(['food','IT']);

  return (
    <div>
      <h3 className="poll-creator-heading">Add Tags:</h3>
      <form
        action=""
        onSubmit={() => {
          if (tags.includes(newTag.current.value.toUpperCase())) {
            console.log("JUZ TAKI TAG JEST:" + newTag.current.value);
          } else {
            setTags([...tags, newTag.current.value.toUpperCase()]);
          }
        }}
      >
        <input type="text" placeholder="add tag here" ref={newTag} />
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
