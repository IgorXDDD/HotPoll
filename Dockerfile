FROM openjdk
EXPOSE 4444
ADD target/hotpoll-0.0.1-SNAPSHOT.jar hotpoll.jar
ENTRYPOINT ["java","-jar","hotpoll.jar"]