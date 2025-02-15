:: 建立构建目录
rmdir /s /q build
mkdir build
mkdir build\cronjob-scheduler
mkdir build\cronjob-scheduler\libs
mkdir build\cronjob-scheduler\etc
mkdir build\cronjob-managerui
mkdir build\cronjob-examples
mkdir build\cronjob-examples\example-executor
mkdir build\cronjob-examples\example-executor\libs
mkdir build\cronjob-examples\example-executor-starter
mkdir build\cronjob-examples\example-executor-starter\libs

:: 开始打包构建maven的各个模块
:: mvn clean package -Dmaven.test.skip=true -U -B

:: 将服务端拷贝至构建目录下
copy cronjob-scheduler\target\libs\* build\cronjob-scheduler\libs
copy cronjob-scheduler\target\cronjob-scheduler*.jar build\cronjob-scheduler
del build\cronjob-scheduler\*-sources.jar
del build\cronjob-scheduler\*-javadoc.jar
copy cronjob-scheduler\bin\* build\cronjob-scheduler
copy cronjob-scheduler\src\main\resources\application.properties build\cronjob-scheduler\etc

:: 将示例工程拷贝至构建目录下
copy cronjob-examples\example-executor\target\libs\* build\cronjob-examples\example-executor\libs
copy cronjob-examples\example-executor\target\*.jar build\cronjob-examples\example-executor
copy cronjob-examples\example-executor\bin\* build\cronjob-examples\example-executor
del  build\cronjob-examples\example-executor\*-sources.jar
del  build\cronjob-examples\example-executor\*-javadoc.jar

:: 将示例工程拷贝至构建目录下
copy cronjob-examples\example-executor-starter\target\libs\* build\cronjob-examples\example-executor-starter\libs
copy cronjob-examples\example-executor-starter\target\*.jar build\cronjob-examples\example-executor-starter
copy cronjob-examples\example-executor-starter\bin\* build\cronjob-examples\example-executor-starter
del  build\cronjob-examples\example-executor-starter\*-sources.jar
del  build\cronjob-examples\example-executor-starter\*-javadoc.jar

:: 构建前端UI
cd cronjob-managerui
del /s /q dist
:: npm install --global yarn
:: npm install --global tyarn
tyarn install | yarn run build
copy dist\* ..\build\cronjob-managerui
del /s /q dist
echo "builder cronjob success."