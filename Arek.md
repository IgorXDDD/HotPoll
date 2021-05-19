# React - an essential tool for a frontend developer


## What is React?
React is a javascript library used to build user interfaces. It allows you to create complex UIs from small and isolated pieces of code - so called "components".
It's Declarative...
Component-Based...

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
Praesent aliquam justo vitae felis ornare, eu faucibus diam molestie. Etiam viverra, purus sed viverra placerat, sapien dolor sollicitudin turpis, sed pellentesque diam lectus non leo. Quisque ac risus in dui faucibus pretium. Mauris nec ligula efficitur, fermentum arcu vel, venenatis nunc. Integer dignissim luctus ante, ut sagittis felis pretium at. Donec ornare enim turpis, sed euismod arcu scelerisque iaculis. Proin quis est accumsan, dictum ipsum a, scelerisque magna. Integer dapibus enim sed egestas elementum. Donec laoreet, erat in eleifend ornare, nulla nibh venenatis mauris, ut semper ante mi ac leo. Sed imperdiet erat elit, sit amet volutpat purus porttitor ac. Ut sollicitudin sit amet ligula sed egestas. Quisque bibendum neque quis auctor ultricies. Aenean a dapibus lectus, id faucibus tortor. Nunc varius ante quis auctor vestibulum.

## Hooks
In venenatis fermentum orci at consequat. Ut ex odio, sagittis eu arcu hendrerit, imperdiet malesuada nibh. Donec vel massa lorem. Aliquam eu sapien urna. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Maecenas lacinia mattis quam non hendrerit. Nullam quis tempus eros. Maecenas imperdiet massa eget mauris rhoncus porttitor. Sed in congue lectus, vitae blandit velit. Aliquam eget nisl semper, venenatis dui sit amet, bibendum turpis.

#### useState()
Maecenas suscipit turpis in lobortis lobortis. Sed ligula massa, convallis non suscipit vel, tincidunt vitae orci. Donec tempus porttitor justo, quis porttitor dui aliquet quis. Aenean non dolor vitae nisi aliquam tempus. Integer cursus magna et eleifend tempor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam blandit viverra elit. Suspendisse potenti. Morbi pretium eget arcu ut bibendum. Proin et malesuada lorem, eu laoreet quam. Sed vitae mattis mauris. Cras leo odio, laoreet nec posuere id, fermentum ac lorem. Donec viverra at diam in cursus. Morbi pharetra egestas vehicula.

#### useEffect()
Ut tincidunt libero dictum nunc pellentesque varius. Phasellus luctus, dui sit amet convallis blandit, velit risus dapibus magna, et condimentum velit risus quis sem. In commodo ut erat interdum auctor. Sed cursus libero eu sapien viverra, et rhoncus nulla imperdiet. Nunc posuere a dolor vitae semper. Duis euismod, mi eget suscipit maximus, neque risus interdum tortor, suscipit sodales diam lectus at dui. Nam et posuere ipsum, ut sagittis nunc. Nulla blandit id nunc a efficitur. Pellentesque pretium varius sapien, nec euismod tortor elementum quis. Phasellus non nunc nec lorem hendrerit fringilla non ac nibh. Nulla posuere vel lectus id gravida. Cras sed nisl consequat est elementum tincidunt. Cras semper ultricies rhoncus. Ut velit odio, ullamcorper vitae turpis id, fringilla varius mauris. Nulla semper ultricies justo, eget varius purus blandit ut.

#### Personalized hooks
have to start with use...
community already created many useful hooks like useFetch, 