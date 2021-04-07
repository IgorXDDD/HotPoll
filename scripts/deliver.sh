#if pgrep hotpoll; then pkill -f hotpoll; fi
pkill -f hotpoll
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar $1  &