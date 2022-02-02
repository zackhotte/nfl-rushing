FROM node:14 AS ui

COPY nfl-rushing-ui .

RUN npm install && \
    npm run build

FROM openjdk:11 AS api

COPY . .

WORKDIR /nfl-rushing-api

RUN apt-get update -y && apt-get install --no-install-recommends -y maven=3.8.4 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=ui build/index.html src/main/resources/templates
COPY --from=ui build src/main/resources/static

RUN mvn -Dmaven.test.skip clean package

CMD ["java", "-jar", "target/nflrushing-0.0.1-SNAPSHOT.jar"]
