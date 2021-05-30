import React from "react";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Welcome from "./Welcome"

const About = () => {
  return (
    <>
    <Welcome/>
      <section className="about">
        <h1>About HotPoll</h1>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Architecto
          sit aut in adipisci beatae eos quasi sint deserunt similique tempora
          pariatur rerum enim, nostrum repellendus quas deleniti ab. Iste,
          ipsum.
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Sit natus
          minima ad voluptatem, dolor laborum, voluptate amet quia libero
          adipisci quas, maiores ea necessitatibus ducimus ut eveniet sint sed
          temporibus fugiat commodi vitae corrupti saepe animi? Amet aut numquam
          ipsa, et rerum id deserunt eveniet?
        </p>
        <p>
          Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum quis,
          quisquam qui culpa odit corrupti, nihil eaque molestias ex quidem quod
          nam architecto velit ipsam.
        </p>
        <p>
          Lorem ipsum dolor, sit amet consectetur adipisicing elit. A deleniti
          dolorem mollitia nemo commodi ex architecto vel expedita distinctio
          sint hic nobis ab, quasi aperiam tempora ducimus sed quaerat sunt
          quibusdam!
        </p>
        {/* <p>
          <Link to="/" className="link-underline">
            Back Home
          </Link>
        </p> */}
      </section>
    </>
  );
};

export default About;
