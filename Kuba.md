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

With this piece of code your server will now print `Greetings form Spring Boot!`. You can test your app already if you want to. Spring Boot has generated all necessary
dependencies. All you have to do now is to put this lines of code in your terminal:

```bash
./mvnw clean install
```

The line above builds and installs your app so you will have to put it before if you made any changes.

```bash
./mvnw spring-boot:run
```

If everything is alright when you go to `localhost:8080` in your browser, you should be given information from spring boot that the [servlet Tomcat](http://tomcat.apache.org/) has started. Notice that we have used `./mvnw`. It is a [maven wrapper](https://github.com/takari/maven-wrapper), which comes with spring boot and is a neat option for portability. It does what a normal maven installation would do but without you having to actually install maven. With such knowledge you could take few things of your deploy container setup (for example [Docker](https://www.docker.com/)).    

In this tutorial I won't be covering frontend so if you want to make your site better looking I can suggest other tutorials from our blog. In order to connect frontend with backend you can follow [this tutorial](https://www.youtube.com/watch?v=RZ8A2Jnxgr4&ab_channel=DevinJapan). Creating new [endpoints](https://smartbear.com/learn/performance-monitoring/api-endpoints/#:~:text=Simply%20put%2C%20an%20endpoint%20is,of%20a%20server%20or%20service.&text=The%20place%20that%20APIs%20send,lives%2C%20is%20called%20an%20endpoint.) for frontend dev is as simple as creating new controllers in Spring, each created with a template:

```java
@RestController
@RequestMapping("/your/api/path")
public class RESTController {

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

What is JWT? [link](https://jwt.io/)  
What is OAuth2? [link](https://oauth.net/2/)  
If you have already setup Spring boot server with controllers of your liking, next step to secure app is to create WebSecurityConfig class.  
Keep in mind that in order to have your own JWT repository and user base you need to setup our own data base and (most recomender) our own userService.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("DefaultUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private CustomOAuth2UserService oauthUserService;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/your/api/1", "/your/api/2").authenticated()
                .and()
                .oauth2Login().defaultSuccessUrl("/").failureUrl("/")
                .userInfoEndpoint().userService(oauthUserService);


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
```
In order for this code to work you need to implement some of used classed and probably include following dependencies in your `pom.xml`:

```xml
	...

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-client</artifactId>
            <version>2.3.3.RELEASE</version>
        </dependency>

	<dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
	...
```

Also if you want to avoid most of the boiler plate code and use only jwt (oauth wont be working) you can use just:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/your/api/1", "/your/api/2").authenticated();


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
```

Now we will implement classes needed for JWT to start working.  

`AuthEntryPointJwt` class
```java
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }

}
```

`AuthTokenFilter` class
```java
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Qualifier("DefaultUserDetailsService")
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
```
`JwtUtils` class
```java
@Component("JwtUtils")
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${your.app.jwtSecret}")
    private String jwtSecret;

    @Value("${your.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
```
Also add this to your application.properties:
```properties
your.app.jwtSecret=secret
your.app.jwtExpirationMs=86400000
```

Now if you want to just use JWT you should give user some way to authenticate themselves. We can achieve this with simple controller:

```java 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

	// just for test if endpoint works
    @GetMapping("/singin")
    public ResponseEntity<?> singinGet() {
        return ResponseEntity.ok("singin");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(JwtResponse.builder()
                        .jwt(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail()).build());
    }
	// just for test if endpoint works
    @GetMapping("/singup")
    public ResponseEntity<?> singupGet() {

        return ResponseEntity.ok("singup");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws ConstraintsViolationException {
        if (userService.findByNickname(signUpRequest.getUsername()).size() != 0) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder().message("Error: Username is already taken!").build());
        }

        if (userService.findByEmail(signUpRequest.getEmail()).size() != 0) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder().message("Error: Email is already in use!").build());
        }

        // Create new user's account
        userService.create(User.builder().nickname(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword())).build());


        return ResponseEntity.ok(MessageResponse.builder().message("User registered successfully!").build());
    }
}
```
Now users can register themselves and login. Login endpoint returnt jwt token which will be needed to be stored in users browser and send from frontend every time they want to access secured endpoint. (Just add to requests header "Authenticate: Bearer <token>") However some of you would want oauth also authenticating users. First we would need to implement some classes:
`CustomOAuth2User` class
```java
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }
}

```
`CustomOAuth2UserService` class
```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }

}
```
`UserDetailsImpl` class
```java
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    public UserDetailsImpl(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword()
                );
    }



    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
```
Ok now that all clasess are implemented we have to tell Spring which oauth servers to use (application.properties):

```properites
spring.security.oauth2.client.registration.<provider>.client-id= <yourid>
spring.security.oauth2.client.registration.<provider>.client-secret= <yoursecret>
```

In order to get such id and secret you can follow [this google tutorial](https://developers.google.com/identity/protocols/oauth2/openid-connect) as example. Great, now if your users want to authenticate with oauth they have to go to `/oauth2/authorization/<provider>`.    
If you followed all the instructions and completed required classed you are now ready to create a simple security for your app.
