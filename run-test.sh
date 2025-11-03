#!/bin/bash

IMAGE_NAME=n-bank-test-image
TEST_PROFILE=${1:-api}
TIMESTAMP=$(date +"%Y%m%d_%H%M")
TEST_OUTPUT_DIR=./test-output/$TIMESTAMP

mkdir -p "$TEST_OUTPUT_DIR/logs"
mkdir -p "$TEST_OUTPUT_DIR/results"
mkdir -p "$TEST_OUTPUT_DIR/reports"

  #
docker build -t $IMAGE_NAME .
docker run --rm \
  -v "$TEST_OUTPUT_DIR/logs":/app/logs \
  -v "$TEST_OUTPUT_DIR/results":/app/target/surefire-reports \
  -v "$TEST_OUTPUT_DIR/reports":/app/target/site \
  -e TEST_PROFILE="$TEST_PROFILE" \
  -e BASEAPIURL="http://backend:4111" \
  -e BASEUIURL="http://frontend:3000" \
  $IMAGE_NAME

echo "Tests finish"
echo "Log file $TEST_OUTPUT_DIR/logs"
echo "Result file $TEST_OUTPUT_DIR/results"
echo "Reports $TEST_OUTPUT_DIR/reports"