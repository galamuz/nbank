# java mvn git - docker hub
FROM maven:3.9.11-eclipse-temurin-25

ARG TEST_PROFILE=api
ARG BASEAPIURL=http://localhost:4111
ARG BASEUIURL=http://localhost:3000

# env container properties
ENV TEST_PROFILE=${TEST_PROFILE}
ENV BASEAPIURL=${BASEAPIURL}
ENV BASEUIURL=${BASEUIURL}

# work directory
WORKDIR /app

# copy pom
COPY pom.xml .
RUN mvn dependency:go-offline

# copy all project
COPY . .

USER root

#
CMD ["/bin/bash", "-c", "\
  mkdir -p /app/logs && \
  echo '>>>>> Running tests with profile ${TEST_PROFILE}' && \
  mvn test -q -P ${TEST_PROFILE} && \
  echo '>>>>> Running surefire-report:report' && \
  mvn -DskipTests=true surefire-report:report \
  > /app/logs/run.log 2>&1"]