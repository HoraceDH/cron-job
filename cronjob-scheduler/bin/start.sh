#!/bin/sh
mkdir logs
gcLogPath="./logs"
if [ "$(uname)" == "Linux" ]; then
    gcLogPath="/dev/shm"
fi
echo "gc log path set is ${gcLogPath}"
jarName=`ls | grep cronjob-scheduler*.jar`
echo "startup jar is ${jarName}"

nohup java -Dfile.encoding=UTF-8 -server \
-Xss512k \
-Xms512M \
-Xmx2048M \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseG1GC \
-XX:ConcGCThreads=4 \
-XX:+PrintGCDetails \
-XX:+PrintFlagsFinal \
-XX:+PrintReferenceGC \
-XX:G1LogLevel=finest \
-XX:MetaspaceSize=128M \
-XX:+PrintGCDateStamps \
-XX:G1HeapRegionSize=4m \
-XX:ParallelGCThreads=4 \
-XX:MaxGCPauseMillis=200 \
-XX:MaxMetaspaceSize=128M \
-XX:+ParallelRefProcEnabled \
-XX:+PrintGCApplicationStoppedTime \
-XX:InitiatingHeapOccupancyPercent=25 \
-XX:+PrintGCApplicationConcurrentTime \
-Xloggc:${gcLogPath}/cronjob-scheduler-gc.log \
-jar ${jarName} \
--spring.config.location=./etc/application.properties > ./logs/console.log 2>&1 &

echo "cronjob-scheduler startup success."
