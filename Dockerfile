FROM node:14 AS ui

WORKDIR /nfl-rushing-ui

RUN npm install && \
    npm run build

COPY build /usr/src/app

FROM openjdk:11 AS api

WORKDIR /nfl-rushing-api

RUN apt-get update -y && apt-get install --no-install-recommends -y maven=3.6.3 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=ui /usr/src/app/index.html src/main/resources/templates
COPY --from=ui /usr/src/app/ src/main/resources/static

RUN mvn -Dmaven.test.skip clean package

CMD ["java", "-jar", "target/nflrushing-0.0.1-SNAPSHOT.jar"]
