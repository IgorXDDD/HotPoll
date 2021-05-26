# React - two directional communication between parent and child component

## Introduction
As You might have read on [Arek page](https://igorxddd.github.io/HotPoll/Arek) React is a JS library used to build interfaces. Component mechanism allows us to build a site 'piece by piece' - in a more objective way.

## Communication between components
By default sharing information between components is a little complicated.
Let's say there's a parent-child relation between two components:
```javascript
function Parent()
{
    ...
    let someData = someValue;
    return (
        <div>
        ...
        <Child/>
        ...
        </div>
    )
}
```
Suppose we want to pass `someData` to `Child` component so it can work on that (alter it in some way) and then use it in `Parent` component. 

How do we do that?

We could try defining `Child` component like that:
```javascript
function Child( {someData} )
{
    ...
}
```
Doing so will allow us to pass `someData` from `Parent` component directly to `Child`:
```javascript
return (
        <div>
        ...
        <Child someData={someData} />
        ...
        </div>
    )
```
After that we will have acces to data stored in `someData` but there's a problem:
`someData` was passed by value, not by reference.

Changing it in a `Child` component will only affect it there, `Parent` is still operating on its own copy of `someData`.

What we've just done is using React prop - it's good enough when we know we want only parent to child communication. For bidirectional one we need something more sophisticated. What about situations in which we're interested in sharing some info between two children of one parent, or even more distant relatives? It would be perfect if we could be able to do all that stuff by one mechanism.

## useContext to save the day

Everything that I mentioned above can be fixed with one React hook - `useContext`.

### What is useContext?
Briefly: it's a React hook that can provide globally defined objects/variables to chosen components.
With `useState` hook it allows us to alter data in easy way.

### How to use useContext?

The most common way to use it is to create separate file `context.js` in which `React` and `useContext` must be imported from `"react"` (importing `useState` is also a good idea).

Once imports are done, we can move on to creating const called `AppContext`:
```javascript
const AppContext = React.createContext();
```
Then in our component function (let's call it `AppProvider`) we can set up some `useStates` that will be our global variables:
```javascript
const AppProvider = ({ children }) => 
{
    const [username,setUsername] = useState('');
    const [isLogged,setIsLogged] = useState(false);
...
}
```
Of course inside out context component there can be defined some functions (we're not limited to use only `useState`).

Once we have everything prepared (our globals) we can define our `return`:
```javascript

...
return (
    <AppContext.Provider 
    value = {
        username,
        setUsername,
        isLogged,
        setIsLogged
    }
    >
        {children}
    </AppContext.Provider>
)
```
After finishing body of our component, one last thing to do is to set up exports, it could be done like that:

```javascript
export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
```
Now our `context.js` file is officially finished.

### Setting up `index.js`
Before we can move on to specific component files, we must modify our `index.js` file.
First thing to do is to import our `AppProvider` from context file:
```javascript
import { AppProvider } from "./context";
```
If our app is defined in some `App.js` file we just need to wrap it in our `ReactDOM.render()` function.
This is how things look like in our project:
```javascript
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { AppProvider } from "./context";

ReactDOM.render(
  <React.StrictMode>
    <AppProvider>
      <App />
    </AppProvider>
  </React.StrictMode>,
  document.getElementById("root")
);
```
### Using context inside components

Once we're inside component file, in which we want to use `useContext` hook, first thing we need to do is to import `useGlobalContext` from `context.js`:
```javascript
import { useGlobalContext } from "../../context";
```

Then, when we're inside function that defines the component, we need to destructure the `useGlobalContext()`:
```javascript
const {username,setUsername} = useGlobalContext();
```
With code above we can both have acces to and modify `username` variable by using related `setUsername` function. 

Changing one of `useContext` variables will change it everywhere it's imported (destructured) from `useGlobalContext()`.

As You've problably noticed: we can use only those variables/functions we're interested in. `context.js` file has to return each of our globals, but once we're in a specific file, we only get those which are useful in specific context (this is probably where the name `useContext` comes from).
