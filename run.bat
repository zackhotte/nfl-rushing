@echo off
title %0

@REM # build react app
cd /d nfl-rushing-ui
call npm install
call npm run build

@REM # Move files to spring resources directory
move build/index.html ../nfl-rushing-api/src/main/resources/templates
move build/* ../nfl-rushing-api/src/main/resources/static

@REM # compile and run spring boot api
cd /d ../nfl-rushing-api
call ./mvnw -Dmaven.test.skip clean package

call java -jar target/nflrushing-0.0.1-SNAPSHOT.jar

