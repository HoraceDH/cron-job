#!/bin/sh
mkdir logs
jarName=`ls | grep example-executor*.jar`
nohup java -Dfile.encoding=UTF-8 -Dcronjob.server.address=http://127.0.0.1 -Dcronjob.tenant.name=horace -Dcronjob.app.name=example-executor -jar ${jarName} > ./logs/console.log 2>&1 &
echo "example-executor start success"