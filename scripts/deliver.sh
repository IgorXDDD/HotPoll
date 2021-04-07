if pgrep -f "java -jar"; then pkill -f "java -jar"; fi
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar ${1} &