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
    <section className="min-height-100vh">
      <div className="info">
        <h2>Plase sign in first!</h2>
        <Link to="/" className="link-underline">
          Go to home page
        </Link>
      </div>
    </section>
  ) : (
    <section>
      <form className="search-form" onSubmit={handleSubmit}>
        <input
          className="search-form-input"
          placeholder="Search"
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
