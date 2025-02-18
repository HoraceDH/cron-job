@echo off
mkdir logs
for /f "delims=" %%i in ('dir /b /s example-executor*.jar') do (
    set "jarName=%%i"
    goto :found
)

:found
echo The JAR file is: %jarName%

start javaw -Dfile.encoding=UTF-8 -Dcronjob.server.address=http://127.0.0.1 -Dcronjob.tenant.name=horace -Dcronjob.app.name=example-executor -jar %jarName%
echo "example-executor start success"