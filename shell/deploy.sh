#!/bin/sh
LOG_HOME=/opt/logs
BASE_HOME=/home/app
#ln -s /opt/data/logs/${POD_NAMESPACE}/${POD_NAME} ${LOG_HOME}
export LOG_HOME="${LOG_HOME}/${POD_NAMESPACE}/${POD_NAME}"
java ${JAVA_OPTS} ${APP_ENV} -jar ${BASE_HOME}/app.jar