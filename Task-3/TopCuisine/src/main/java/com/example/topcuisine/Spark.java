package com.example.topcuisine;

import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class Spark {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        InputStream input = Spark.class.getClassLoader().getResourceAsStream("application.properties");
        props.load(input);

        SparkSession spark = SparkSession.builder()
                .appName(props.getProperty("app.name"))
                .master(props.getProperty("spark.master"))
                .getOrCreate();

        Dataset<Row> df = spark.read()
                .option("header", "true")
                .csv(props.getProperty("hdfs.file.path"));

        Dataset<Row> cuisineCounts = df
                .groupBy("cuisine_type")
                .agg(functions.count("restaurant_name").alias("restaurant_count"));

        Dataset<Row> sortedCuisineCounts = cuisineCounts.orderBy(functions.col("restaurant_count").desc());

        Dataset<Row> top10CuisineCounts = sortedCuisineCounts.limit(10);

        top10CuisineCounts.show();

        top10CuisineCounts.write().mode("overwrite").csv(props.getProperty("hdfs.output.path"));

        spark.stop();
    }
}