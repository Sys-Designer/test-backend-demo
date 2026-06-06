#!/usr/bin/env bash
set -e
DIR=$(realpath "$(dirname "$0")")
cd $DIR/../
WORKDIR=$(pwd)
output_path=$WORKDIR/output/product/
while getopts ":o:" opt
do
    case $opt in
        o)
            output_path=$OPTARG;;
        ?)
            echo "Unknown parameter"
            exit 1;;
    esac
done
if [ -d $output_path ]; then
  rm -rf $output_path
fi
mkdir -p $output_path
mkdir $output_path/app
mkdir $output_path/bin
mkdir $output_path/conf
mkdir $output_path/data
mkdir $output_path/logs
mkdir $output_path/plugins
cp $WORKDIR/scripts/startup.* $output_path/bin/
export OUTPUT_PRODUCT_PLUGIN_PATH=$output_path/plugins;
if [ -z "${MAIN_APP_PROJECT:-}" ]; then
  MAIN_APP_PROJECT=$WORKDIR/idesign-platform-web
fi
if [ -d $MAIN_APP_PROJECT ]; then
  cp $MAIN_APP_PROJECT/target/*.jar $output_path/app/app.jar
  cp $MAIN_APP_PROJECT/src/main/resources/*.properties $output_path/conf
  if [ -f "$MAIN_APP_PROJECT/src/main/resources/logback*.xml" ]; then
    cp $MAIN_APP_PROJECT/src/main/resources/logback*.xml $output_path/conf
  fi
fi

if [ -d "$WORKDIR/plugins" ]; then
  for file in $WORKDIR/plugins/*; do
    filename=$(basename $file)
    if [ -f $WORKDIR/plugins/${filename}/target/*.jar ]; then
      cp $WORKDIR/plugins/${filename}/target/*.jar $output_path/plugins/
    fi
  done
fi
cd $WORKDIR
output_app_name=""
if [ -z "${APP_NAME:-}" ]; then
  output_app_name=$(pwd -P|awk -F'/' '{print $NF}')
else
  output_app_name=$APP_NAME
fi
cd $output_path
zip -rlv "../app.zip" .
export APP_PRODUCT_PACK_OUTPUT_PRODUCT_PATH=$output_path../app.zip
echo "zip success create $APP_PRODUCT_PACK_OUTPUT_PRODUCT_PATH."
