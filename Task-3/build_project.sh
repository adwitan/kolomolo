#!/bin/bash
set -e
cd CsvUpload

mvn clean package

cd ../
cd TopCuisine

mvn clean package

cd ../

cp -rf TopCuisine/target/TopCuisine-1.0-SNAPSHOT-jar-with-dependencies.jar Docker/spark
cp -rf CsvUpload/target/CsvUpload-1.0-SNAPSHOT-jar-with-dependencies.jar Docker/spark

cd Docker/spark

echo "Build spark image"
docker build -t spark -f Dockerfile .

cd ../../

cd SparkAppIntegration

echo "build project"
mvn clean install

echo "Build spring boot app"
docker build -t myapp -f Dockerfile .

cd ../

cd Docker/compose/

docker-compose up -d