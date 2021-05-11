import React from 'react';
const List = ({ items, removeItem, editItem }) => {
  return (
    <div className='grocery-list'>
      {items.map((item) => {
        const { id, title } = item;
        return (
          <article className='poll-option-item' key={id}>
            <p className='title'>{title}</p>
            <div className='btn-container'>
              <button
                type='button'
                className='edit-btn'
                onClick={() => editItem(id)}
              >
                edytuj
              </button>
              <button
                type='button'
                className='delete-btn'
                onClick={() => removeItem(id)}
              >
                usun
              </button>
            </div>
          </article>
        );
      })}
    </div>
  );
};

export default List;
