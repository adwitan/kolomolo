#!/bin/bash

mvn clean package

docker build -t chatapp -f Dockerfile .

docker run -d -p 8882:8500 --name chatapp chatapp
