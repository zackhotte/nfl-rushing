FROM node:14 AS ui

WORKDIR /src/app/ui

COPY nfl-rushing-ui .

RUN npm install

ARG API_URL="http://localhost:8080"
RUN REACT_APP_API_URL=$API_URL npm run build

FROM openjdk:11 AS api

WORKDIR /src/app/api

COPY nfl-rushing-api .

RUN apt-get update -y && apt-get install --no-install-recommends -y maven \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=ui /src/app/ui/build/index.html src/main/resources/templates/index.html
COPY --from=ui /src/app/ui/build src/main/resources/static

RUN mvn -Dmaven.test.skip clean package

CMD ["java", "-jar", "target/nflrushing-0.0.1-SNAPSHOT.jar"]
