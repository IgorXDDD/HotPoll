# React - an essential tool for a frontend developer


## What is React?
React is a javascript library used to build user interfaces. It allows you to create complex UIs from small and isolated pieces of code - so called "components".  

React is declarative which means when you write a component, you just tell React what the DOM should look like, and just let React handle it from there on its own. 

## JSX
JSX is a syntax extension to JavaScript. It's used by React to describe what the UI should look like. As you can see, it's very similar to plain HTML, but it comes with the full power of JavaScript.
```JSX
const example = <h1>Hello, world!</h1>;
```
Nothing special up there, right? But watch this:
```JSX
const name = 'Arek';
const example = <h1>Hello, {name}</h1>
```
JSX allows to inject any valid JavaScript code into HTML! You just need to put it inside the curly braces.

## Components
You can think of the React components as building blocks from which you can compose more or less advanced applications. But what are they *exactly*, like, in code?  

Truth is, the React components are just plain JavaScript functions! But there has to be **some** difference, right? And there is. It lies in the way you use those functions. React Components are functions that you don't invoke yourself - React library decides when to invoke them. That makes rendering of components declarative.  

Let's take a look at an example. 
```JS
import React from "react";

const Footer = () => {
  return <footer>Footer Component</footer>;
};

export default Footer;
```
First, we need to import React from react library. Then we can declare our component - Footer in this case. Notice how the the first letter of the component's name is capitalized - this way React will be able to differentiate between components and plain HTML tags (because you can insert React components in JSX just as html tags; you'll see in a moment).  

In the function's body we can do pretty much everything there's to do in a JS function - declare other functions, variables etc. Additionally we can use *hooks* that allow us to use *state* and other React features.  

The function component has to return JSX. In the example above it's a simple footer tag with some text inside, but generally JSX can be much more complex.  

Lastly, we export the component to conveniently use it in other elements of our app.  

Now, take a look at the Home component.
```JS
import React from "react";
import { Link } from "react-router-dom";
import Welcome from "../components/Welcome";
import LoginOrCreateAccount from "../components/LoginOrCreateAccount";
import Footer from "../components/Footer";

const Home = () => {
  return (
    <>
      <div className="homescreen">
        <Link to="/about">About</Link>
        <section className="homescreen-content">
          <Welcome />
          <LoginOrCreateAccount />
        </section>
      </div>
      <Footer />
    </>
  );
};

export default Home;
```
As you can see, the Footer component (among others like Welcome or LoginOrCreateAccount) is used here just as self-closing HTML tag. You can create many small components and then use them to build something bigger just like that.

## Hooks
In venenatis fermentum orci at consequat. Ut ex odio, sagittis eu arcu hendrerit, imperdiet malesuada nibh. Donec vel massa lorem. Aliquam eu sapien urna. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Maecenas lacinia mattis quam non hendrerit. Nullam quis tempus eros. Maecenas imperdiet massa eget mauris rhoncus porttitor. Sed in congue lectus, vitae blandit velit. Aliquam eget nisl semper, venenatis dui sit amet, bibendum turpis.

#### useState()
Maecenas suscipit turpis in lobortis lobortis. Sed ligula massa, convallis non suscipit vel, tincidunt vitae orci. Donec tempus porttitor justo, quis porttitor dui aliquet quis. Aenean non dolor vitae nisi aliquam tempus. Integer cursus magna et eleifend tempor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam blandit viverra elit. Suspendisse potenti. Morbi pretium eget arcu ut bibendum. Proin et malesuada lorem, eu laoreet quam. Sed vitae mattis mauris. Cras leo odio, laoreet nec posuere id, fermentum ac lorem. Donec viverra at diam in cursus. Morbi pharetra egestas vehicula.

#### useEffect()
Ut tincidunt libero dictum nunc pellentesque varius. Phasellus luctus, dui sit amet convallis blandit, velit risus dapibus magna, et condimentum velit risus quis sem. In commodo ut erat interdum auctor. Sed cursus libero eu sapien viverra, et rhoncus nulla imperdiet. Nunc posuere a dolor vitae semper. Duis euismod, mi eget suscipit maximus, neque risus interdum tortor, suscipit sodales diam lectus at dui. Nam et posuere ipsum, ut sagittis nunc. Nulla blandit id nunc a efficitur. Pellentesque pretium varius sapien, nec euismod tortor elementum quis. Phasellus non nunc nec lorem hendrerit fringilla non ac nibh. Nulla posuere vel lectus id gravida. Cras sed nisl consequat est elementum tincidunt. Cras semper ultricies rhoncus. Ut velit odio, ullamcorper vitae turpis id, fringilla varius mauris. Nulla semper ultricies justo, eget varius purus blandit ut.

#### Personalized hooks
have to start with use...
community already created many useful hooks like useFetch, 