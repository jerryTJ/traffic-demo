FROM docker.io/library/eclipse-temurin:21.0.7_6-jdk-alpine-3.21
LABEL maintainer="jerry"
LABEL REPOSITORY=development-office/demo
LABEL TAG=1.0.0
ARG BASE_HOME=/home/app
ARG GROUP=appgroup
ARG USER=app
ARG GID=1000
ARG UID=1000
RUN groupadd -g ${GID} ${GROUP} && useradd -g ${GROUP}  --uid=${UID} -d ${BASE_HOME} ${USER}
WORKDIR ${BASE_HOME}
USER ${USER}
COPY shell/ ${BASE_HOME}/shell/
COPY ./probe-demo/build/libs/*.jar ${BASE_HOME}/app.jar
EXPOSE 8080
CMD ["/home/app/shell/deploy.sh"]