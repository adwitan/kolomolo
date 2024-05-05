package com.example.kolomolo.SparkAppIntegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spark")
public class SparkConfig {
    private String master;
    private String deploymode;
    private String home;
    private String hdfs;
    private String version;

}