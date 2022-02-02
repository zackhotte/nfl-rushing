#!/bin/bash

# build react app
cd nfl-rushing-ui
npm install
npm run build

# Move files to spring resources directory
rsync -av --progress build/index.html ../nfl-rushing-api/src/main/resources/templates
rsync -av --progress build/* ../nfl-rushing-api/src/main/resources/static --exclude index.html

# compile and run spring boot api
cd ../nfl-rushing-api
./mvnw -Dmaven.test.skip clean package

java -jar target/nflrushing-0.0.1-SNAPSHOT.jar