@echo off
mkdir logs

for /f "delims=" %%i in ('dir /b /s cronjob-scheduler*.jar') do (
    set "jarName=%%i"
    goto :found
)

:found
echo The JAR file is: %jarName%

start javaw -Dfile.encoding=UTF-8 -server ^
-Xss512k ^
-Xms512M ^
-Xmx2048M ^
-XX:+UnlockExperimentalVMOptions ^
-XX:+UseG1GC ^
-XX:ConcGCThreads=4 ^
-XX:+PrintGCDetails ^
-XX:+PrintFlagsFinal ^
-XX:+PrintReferenceGC ^
-XX:G1LogLevel=finest ^
-XX:MetaspaceSize=128M ^
-XX:+PrintGCDateStamps ^
-XX:G1HeapRegionSize=4m ^
-XX:ParallelGCThreads=4 ^
-XX:MaxGCPauseMillis=200 ^
-XX:MaxMetaspaceSize=128M ^
-XX:+ParallelRefProcEnabled ^
-XX:+PrintGCApplicationStoppedTime ^
-XX:InitiatingHeapOccupancyPercent=25 ^
-XX:+PrintGCApplicationConcurrentTime ^
-jar %jarName% ^
--spring.config.location=./etc/application.properties

echo "cronjob-scheduler startup success."