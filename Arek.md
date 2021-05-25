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
It used to be that components in React were written as classes rather than functions. It wasn't very convenient and there were some problems with that approach.  
Function components and hooks were introduced in 2018 with React 16.8.0.  

Hooks are special functions that allow you to use *state* and other features of React. Let's take a look at the most popular hooks - *useState* and *useEffect*.


### **useState()**
```JS
import React, { useState } from 'react';

function Counter() {

  const [count, setCount] = useState(0);

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );
}
```
We call *useState* hook inside a function component to add some local state to it. React will preserve this state between re-renders.  

*useState* function returns a pair: a new state variable and a function that lets you update it. The only argument to *useState* is the initial state which can be of any valid JS type - number, string, boolean, object, null.  

You can use the State Hook more than once in a sibgle component. Generally you can name state variables and their update functions whatever you'd like but a good practice is to name the update function as set*Variable_name*, e.g. count and setCount, name and setName or isLoading and setIsLoading.  


### **useEffect()**
```JS
import React, { useState, useEffect } from 'react';

function Counter() {
  const [count, setCount] = useState(0);

  useEffect(() => {
    // Update the document title using the browser API
    document.title = `You clicked ${count} times`;
  });

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );
}
```
Data fetching, subscriptions, manual changing of DOM from React components - all these operations can be described as side effects because they can affect other components and can't be done during rendering.  

The *useEffect* hook allows you to perform side effects from a function component. The example above uses *useEffect* to dynamically update the site's title based on a count value.  

The *useEffect* hook is a function that takes another function as an argument. The arrow function notation from JS ES6 is very helpful here.

It's optional, but you can also specify second argument. It should be a list of dependencies. In other words a list of these variables that, when changed, should invoke *useEffect* to run. Passing in an empty list is a good way to say "do it only once after the first render".

### **Personalized hooks**
community already created many useful hooks like useFetch, 

Sometimes, we want to reuse some stateful logic between components. Traditionally, there were two popular solutions to this problem: higher-order components and render props. Custom Hooks let you do this, but without adding more components to your tree.  

Before you can start writing your own hooks, you have to learn some rules:
- You can call hooks only at the top level. Forget about calling them inside loops, conditions or nested functions.
- You can call hooks only from React function components or your own custom hooks. Don't call hooks from regular JavaScript funtions.
- Stick to the *useSomething* naming convention.

And one last thing. Community already created many useful hooks, e.g. useFetch that makes it even easier to fetch data, useLocalStorage that simplifies the storage and retrieval of data from localStorage or more exotic ones like useSpeechRecognition and useSpeechSynthesis.  

So before you get to work, check if the thing you want to do hasn't already been implemented as a custom hook. Because chances are it has.

Happy coding :)