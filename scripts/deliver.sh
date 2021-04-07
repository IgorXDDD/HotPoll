if pgrep -f hotpoll; then pkill -f hotpoll; fi
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar ${1} &