#!/bin/sh

# 建立构建目录
rm -rf build
mkdir build
mkdir build/cronjob-scheduler
mkdir build/cronjob-scheduler/etc
mkdir build/cronjob-managerui
mkdir build/cronjob-examples
mkdir build/cronjob-examples/example-executor
mkdir build/cronjob-examples/example-executor-starter

# 开始打包构建maven的各个模块
mvn clean package -Dmaven.test.skip=true -U -B

# 将服务端拷贝至构建目录下
cp -r cronjob-scheduler/target/libs build/cronjob-scheduler
cp cronjob-scheduler/target/cronjob-scheduler*.jar build/cronjob-scheduler
rm -rf build/cronjob-scheduler/*-sources.jar
rm -rf build/cronjob-scheduler/*-javadoc.jar
cp cronjob-scheduler/bin/* build/cronjob-scheduler
chmod +x build/cronjob-scheduler/*.sh
cp cronjob-scheduler/src/main/resources/application.properties build/cronjob-scheduler/etc

# 将示例工程拷贝至构建目录下
cp -r cronjob-examples/example-executor/target/libs build/cronjob-examples/example-executor
cp -r cronjob-examples/example-executor/target/*.jar build/cronjob-examples/example-executor
cp -r cronjob-examples/example-executor/bin/* build/cronjob-examples/example-executor
rm -rf  build/cronjob-examples/example-executor/*-sources.jar
rm -rf  build/cronjob-examples/example-executor/*-javadoc.jar
chmod +x build/cronjob-examples/example-executor/*.sh

# 将示例工程拷贝至构建目录下
cp -r cronjob-examples/example-executor-starter/target/libs build/cronjob-examples/example-executor-starter
cp -r cronjob-examples/example-executor-starter/target/*.jar build/cronjob-examples/example-executor-starter
cp -r cronjob-examples/example-executor-starter/bin/* build/cronjob-examples/example-executor-starter
rm -f  build/cronjob-examples/example-executor-starter/*-sources.jar
rm -f  build/cronjob-examples/example-executor-starter/*-javadoc.jar
chmod +x build/cronjob-examples/example-executor-starter/*.sh

# 构建前端UI
cd cronjob-managerui
rm -rf dist
# npm install --global yarn
# npm install --global tyarn
tyarn install
yarn run build
cp -r dist/* ../build/cronjob-managerui
rm -rf dist
echo "builder cronjob success."

