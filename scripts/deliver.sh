#if pgrep hotpoll; then pkill -f hotpoll; fi
pkill -f hotpoll
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar target/hotpoll-0.0.1-SNAPSHOT.jar  &