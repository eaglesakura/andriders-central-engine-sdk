#! /bin/sh

COMPILE_DIR="./src/main/protobuf"
TARGET_FILES="${COMPILE_DIR}/*.proto"
JAVAOUT_DIR="./src/main/generated/protobuf/"

for filepath in ${TARGET_FILES}; do
  echo "compile -> $filepath"
  protoc "--java_out=${JAVAOUT_DIR}" "--proto_path=${COMPILE_DIR}" "${filepath}"
done
