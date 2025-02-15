#!/bin/sh
mvn clean deploy -pl cronjob-commons,cronjob-executor,cronjob-executor-starter -am -Dmaven.test.skip=true -U
