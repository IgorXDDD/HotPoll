import React from 'react';
import { Link } from 'react-router-dom';
import Welcome from './Welcome';

const About = () => {
  return (
    <>
      <Welcome />
      <section className="about">
        <p>
          HotPoll offers the best of Reddit and Twitter - the ability to stay up
          to date with the latest polls posted by users around the world, as
          well as access to a list of the most interesting and popular questions
          of the day. In addition, the service allows you to search for
          interesting content by the name of the poll and by the tag added to
          the post.
        </p>
        <p>
          <Link to="/" className="link-underline">
            Back Home
          </Link>
        </p>
      </section>
    </>
  );
};

export default About;
