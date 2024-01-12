#!/bin/bash

cd 3rdparty/libstr
./build.sh
cd - 
mkdir -p src/main/resources/lib/
cp 3rdparty/libstr/bin/libstrcpp.so src/main/resources/lib/
mvn clean package
