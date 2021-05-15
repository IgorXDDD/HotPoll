## Spring tutorial
If you want to create your own web application nowadays, your most likely to be recomended to use [Spring](https://spring.io/) as your backend technology.
In this tutorial I will show you how to create simple app just like out HotPoll, which was also based on Spring (Java).

### Simple server setup

For starters you can quickly setup your own Spring serwer using [this spring Initializr](https://start.spring.io/), which will let you customize your starting configuration.  

![Initializr](/images/initializr.png)

Next step is to create index controller class. Spring controllers are using [REST API](https://www.redhat.com/en/topics/api/what-is-a-rest-api) for comunication with world, so if you want to create such server you should know at least what GET and POST requests are ([usefull link](https://www.smashingmagazine.com/2018/01/understanding-using-rest-api/)).

```java
package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}
```

With this piece of code your server will now print `<Greetings form Spring Boot!>`. You can test your app already if you want to. Spring Boot has generated all necessary
dependencies. All you have to do now is to put this lines of code in your terminal:

```bash
./mvnw clean install
```

The line above builds and installs your app so you will have to put it before if you made any changes.

```bash
./mvnw spring-boot:run
```

If everything is alright when you go to `<localhost:8080>` in your browser, you should be given information from spring boot that the [servlet Tomcat](http://tomcat.apache.org/) has started. Notice that we have used `<./mvnw>`. It is a [maven wrapper](https://github.com/takari/maven-wrapper), which comes with spring boot and is a neat option for portability. It does what a normal maven installation would do but without you having to actually install maven. With such knowledge you could take few things of your deploy container setup (for example [Docker](https://www.docker.com/)).    

In this tutorial I won't be covering frontend so if you want to make your site better looking I can suggest other tutorials from our blog. In order to connect frontend with backend you can follow [this tutorial](https://www.youtube.com/watch?v=RZ8A2Jnxgr4&ab_channel=DevinJapan). Creating new [endpoints](https://smartbear.com/learn/performance-monitoring/api-endpoints/#:~:text=Simply%20put%2C%20an%20endpoint%20is,of%20a%20server%20or%20service.&text=The%20place%20that%20APIs%20send,lives%2C%20is%20called%20an%20endpoint.) for frontend dev is as simple as creating new controllers in Spring, each created with a template:

```java
@RestController
@RequestMapping("/your/api/path")
public class HelloController {

	@GetMapping("/path")
	public String get() {
    ...
		return "GET!";
	}
  
  @PostMapping("/path")
	public String post(@RequestBody Request request) {
    ...
		return "POST!";
	}

}
```

For more info on REST Spring you can start with [this tutorial](https://spring.io/guides/tutorials/rest/).    
Congratulations you now can create your own Spring server :)

### Simple JWT security with OAuth2

If you have already setup Spring boot server with controllers of your liking, next step to secure app is to create WebSecurityConfig class.
