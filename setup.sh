#! /usr/bin/env bash

# Compile Auth-Fast and build docker image
cd auth-fast/
mvn clean package
docker build --no-cache --tag auth-fast:latest .

# Compile Products and build docker image
cd ../products/
mvn clean package
docker build --no-cache --tag products:latest .

# Build custom Logstash image
cd ../kibana/logstash
docker build --no-cache --tag logstash:latest .

