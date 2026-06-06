#!/usr/bin/env bash
set -e
DIR=$(realpath "$(dirname "$0")")
cd $DIR/../
WORKDIR=$(pwd)
function build_model() {
    cd $WORKDIR/$1
    if [ -f "scripts/build.sh" ]; then
        if [ ! -d "$WORKDIR/$1" ]; then
          echo "$WORKDIR/$1 not exists."
          return
        fi
        cd scripts/
        source ./build.sh
        return
    fi
    echo "Start build $1"
    mvn clean
    mvn install
    echo "Build $1 success."
    cd $WORKDIR
}

echo "Start build projects..."
build_model com-test-api
build_model com-test-lib
build_model com-test-core
build_model com-test-db
cd com-test-web
mvn clean
mvn package
echo "Build projects success."

if [ -d "$WORKDIR/plugins" ]; then
  unset APP_MAIN_HOME
  echo "Building plugins..."
  for file in $WORKDIR/plugins/*; do
    filename=$(basename $file)
    cd $WORKDIR/plugins/$filename
    sh build.sh
  done
  echo "Build plugins finish"
fi
