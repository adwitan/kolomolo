package com.example.kolomolo.SparkAppIntegration.service;


import com.example.kolomolo.SparkAppIntegration.client.SparkApiClient;
import com.example.kolomolo.SparkAppIntegration.config.SparkConfig;
import com.example.kolomolo.SparkAppIntegration.model.Cuisine;
import com.example.kolomolo.SparkAppIntegration.model.LastResultBean;
import com.example.kolomolo.SparkAppIntegration.model.SubmitRequest;
import com.example.kolomolo.SparkAppIntegration.model.SubmitResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;


@Service
@RequiredArgsConstructor
public class SparkService {

    @Value("${file.dir}")
    private String fileDir;
    private final SparkConfig sparkConfig;
    private final LastResultBean lastResultBean;
    private final SparkApiClient sparkApiClient;

    @Async
    public SubmitResponse submitJob(String appResource, String mainClass, String appName) {
        Map<String,String> sparkProperties = new HashMap<>();

        sparkProperties.put("spark.master", sparkConfig.getMaster());
        sparkProperties.put("spark.app.name", appName);

        SubmitRequest submitRequest = SubmitRequest.builder()
                .appResource(appResource)
                .sparkProperties(sparkProperties)
                .clientSparkVersion(sparkConfig.getVersion())
                .mainClass(mainClass)
                .action("CreateSubmissionRequest")
                .build();

        return sparkApiClient.submit(submitRequest);
    }

    @Async
    public void syncResult() throws Exception {
        List<Cuisine> cuisines = new ArrayList<>();
        SparkSession spark = SparkSession.builder()
                .appName("Read CSV from HDFS")
                .master(sparkConfig.getMaster())
                .getOrCreate();


        FileSystem fs = FileSystem.get(new URI(sparkConfig.getHdfs()), spark.sparkContext().hadoopConfiguration());

        try {
            Path csvFilePath = fs.globStatus(new Path("/result/" + "*.csv"))[0].getPath();

            Dataset<Row> data = spark.read().csv(csvFilePath.toString());

            List<Row> rows = data.collectAsList();
            for (Row row : rows) {
                cuisines.add(Cuisine.mapRowToCuisine(row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            spark.stop();
        }
        lastResultBean.setLastResult(cuisines);
    }

    public List<Cuisine> getLastResult() {
        return lastResultBean.getLastResult();
    }
}
