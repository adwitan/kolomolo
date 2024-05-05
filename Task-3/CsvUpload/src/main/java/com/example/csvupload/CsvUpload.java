package com.example.csvupload;


import lombok.var;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class CsvUpload {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        InputStream input = CsvUpload.class.getClassLoader().getResourceAsStream("application.properties");
        props.load(input);

        StructType schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("order_id", DataTypes.StringType, true),
                DataTypes.createStructField("customer_id", DataTypes.StringType, true),
                DataTypes.createStructField("restaurant_name", DataTypes.StringType, true),
                DataTypes.createStructField("cuisine_type", DataTypes.StringType, true),
                DataTypes.createStructField("cost_of_the_order", DataTypes.DoubleType, true),
                DataTypes.createStructField("day_of_the_week", DataTypes.StringType, true),
                DataTypes.createStructField("rating", DataTypes.StringType, true),
                DataTypes.createStructField("food_preparation_time", DataTypes.IntegerType, true),
                DataTypes.createStructField("delivery_time", DataTypes.IntegerType, true)
        });


        SparkSession spark = SparkSession.builder()
                .appName(props.getProperty("app.name"))
                .master(props.getProperty("spark.master"))

                .getOrCreate();

        Dataset<Row> df = spark.read()
                .format("csv")
                .schema(schema)
                .option("header", true)
                .load(props.getProperty("hdfs.source"));

        var numPartitions = Integer.parseInt(props.getProperty("hdfs.partition"));

        Dataset<Row> repartitionedDF = df.repartition(numPartitions);

        repartitionedDF.write()
                .format("csv")
                .option("header", true)
                .save(props.getProperty("hdfs.output.path"));

        spark.stop();
    }
}