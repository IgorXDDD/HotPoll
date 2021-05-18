# Spring features
In this entry I'm going to share my amazement regarding Spring features I've recently had occasion to work with. This is a piece of text from an utter newbie in the world of frameworks and mighty annotations who has just got to know a few of them. So the main focus here is falling on particular features I find very handy, instead of working with Spring as a whole.
## Spring Data - MongoDB
Realworld business applications are usually ment to store, process and conveniently visualise buisiness logic data of various nature. There is a great deal of architectural and project-wise approaches to make development of such systems easy and efficient. No matter of architecture though, there is always an inherent need of performing CRUD operations on the application's data stored in a database. It should be no wonder then, that Spring helps provides some tools for this kind of operations, but still I am in wonder how easy they are with Spring. Just look:
- Neccessary import
  `import org.springframework.data.mongodb.repository.MongoRepository;`
- Object init - conveniently autowired when part of a custom class
``` 
private final MongoRepository<DataClass, String> mr;
    @Autowired
    public Constuctor(final MongoRepository<DataClass, String> mr){
        this.mr = mr;
    }
```
- Usage - find example
```
String id = "123"; #id of sought object
Optional<DataClass> obj = mr.findById(id);
```
That's it! Couple of lines of code and we're able to successfully make use of MongoDB. Obviously MongoRepository interface comes with plenty of more built-in operations we can use: count, delete, deleteAll, deleteAll, deleteAllById, deleteById, existsById, findAllById, findById, save, exists, findAll, findOne, insert, saveAll. This list fully satisfies most of developers' needs, if not however, have no fear - customization, criteria queries, etc. are done just as easy as the operations above.

## Lombok - annotations for business logic objects (POJOs)

That's the part I like the most. Actually not a part of Spring Framework, but really easy to integrate with it. Designing domain objects really comes down to specifying variables it contains and adding couple of annotations. That's enough to design fully functional class with usage-profile according to any developers' needs. Here's an example:
`import lombok.*;`
```
@Data
@Document(collection = "users")
@Builder(builderClassName = "UserBuilder")
public class User {
    @Id
    private final String id;
    @NotNull
    private final String nickname;
    private final String email;
    @NotNull
    private final String password;
    
    public static class UserBuilder {
    }
}
```
Usage:
```
User user = User.builder().password("password").email("email@com").id("123").build();
user.getId(); #getter usage
user.setId("1234"); #ERROR 
```
We've got a class implemented as [this fantastic object design pattern Builder](https://refactoring.guru/design-patterns/builder) with just one annotation.
@Data is another super smart annotation in the example -  it generates all the boilerplate that is normally associated with simple POJOs (Plain Old Java Objects) and beans: getters for all fields, setters for all non-final fields, and appropriate toString, equals and hashCode. As shown above - we can use generated getters, but not setters as variables are **final** - exactly as the developer intended.

@Document and @Id annotations are in fact part of SpringData MongoDB module, yet attached to domain classes such as this one. They provide an information to SpringData what is the name of Mongo [collection](https://docs.mongodb.com/manual/reference/glossary/#std-term-collection) we want to store an object as an [document](https://docs.mongodb.com/manual/core/document/) with an ID specified by @Id.

## Jackson (JSON) Mapper

Now, say we have to make our data available to some other entities as REST API. Objects representing our data that are to be sent must meet arbitrary requiremetns e.g. ignore certain variables or be represented in a non-standard manner. Most likely all of it can be well done by Jackson annotations. Examplary usage:
`import com.fasterxml.jackson.*;`
- client - e.g. inside controller module class
```
    private final ObjectMapper objectMapper;

    @Autowired
    public Controller(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
```

- domain object
```
@Data
@Builder(builderClassName = "UserBuilder")
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {

    private final String id;
    @NotNull
    private final String nickname;
    private final String email;
    @NotNull
    @JsonIgnore
    private final String password;
    
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull
    private final LocalDateTime date;
    
    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
    }
}
```
Now the our controller module will automatically serialize and deserialize our User objects to JSON and back in a way we intended through annotations - it's going to ignore password variable and format the date var. Deserialization (JSON to object) is processed in accordance with the builder pattern thanks to @JsonPOJOBuilder @JsonDeserialize annotations.

## Summary
Working with Spring and other utility Java projects might be cause a shock to newcomers that are used to low-level, do-everything-yourself model of programming. There is a significant cost of entry into this mighty technology - one has to acquaintance yourself with the abilities provided by the framework - be aware what could and should be done with given annotation. After this transitional period developing applications becomes incredibly fast and easy; developing starts to resemble building toy blocks, as everything is ready, we just need to put it together. 




