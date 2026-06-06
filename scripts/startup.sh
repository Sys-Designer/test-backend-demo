#!/usr/bin/env bash
set -e
DIR=$(realpath "$(dirname "$0")")
cd $DIR/
WORKDIR=$(pwd)
export SERVER="com-test"

if [ -z "${JAVA_OPT:-}" ]; then
  JAVA_OPT="-Xmx512m -Xmn256m"
fi

export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $WORKDIR/..; pwd`
DIR=`basename $(pwd)`
if [[ $DIR == "bin" ]]; then
  cd ../
fi
JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/app/app.jar"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${BASE_DIR}/conf/application.properties"
LOG_FILE="classpath:log/default-logback.xml"
if [ -f "${BASE_DIR}/conf/logback.xml" ]; then
 LOG_FILE="${BASE_DIR}/conf/logback.xml"
fi
JAVA_OPT="${JAVA_OPT} --logging.config=${LOG_FILE}"
if [ ! -z "${START_OPT:-}" ]; then
  JAVA_OPT="${JAVA_OPT} ${START_OPT}"
fi
if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

echo "$JAVA ${JAVA_OPT}"
if [ ! -f "${BASE_DIR}/logs/start.out" ]; then
  touch "${BASE_DIR}/logs/start.out"
fi
echo "$JAVA ${JAVA_OPT}" > ${BASE_DIR}/logs/start.out 2>&1 &
nohup $JAVA ${JAVA_OPT} app.${SERVER} >> ${BASE_DIR}/logs/start.out 2>&1 &
echo "app is starting,you can check the ${BASE_DIR}/logs/start.out"
