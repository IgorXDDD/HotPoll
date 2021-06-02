import React, { useState } from 'react';
import { Search } from '../components/Search';
import { useGlobalContext } from '../context';
import { Link } from 'react-router-dom';

export default function SearchForm() {
  const [searchTerm, setSearchTerm] = useState('');
  const searchValue = React.useRef('');
  const { logged } = useGlobalContext();

  React.useEffect(() => {
    if (searchValue.current) {
      searchValue.current.focus();
    }
  }, []);

  function searchPoll() {
    setSearchTerm(searchValue.current.value);
  }
  function handleSubmit(e) {
    e.preventDefault();
  }

  return !logged ? (
    <div className="info">
      <h2>Plase sign in first!</h2>
      <Link to="/" className="link-underline">
        <button>Go to home page</button>
      </Link>
    </div>
  ) : (
    <section>
      <form className="search-form" onSubmit={handleSubmit}>
        <label className="search-form-label" htmlFor="name">
          search poll name
        </label>
        <input
          className="search-form-input"
          type="text"
          name="name"
          id="name"
          ref={searchValue}
          onChange={searchPoll}
        />
      </form>
      <Search searchTerm={searchTerm.toString()} />
    </section>
  );
}
