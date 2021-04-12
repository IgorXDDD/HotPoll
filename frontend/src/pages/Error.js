import React from "react";
import { Link } from "react-router-dom";

const Error = () => {
  return (
    <div className="error-page">
      <h1>Oops! Something went wrong...</h1>
      <Link to="/">Back Home</Link>
    </div>
  );
};

export default Error;
