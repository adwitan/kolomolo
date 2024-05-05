package com.example.kolomolo.SparkAppIntegration.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SubmitRequest {
    private String appResource;
    private Map<String, String> sparkProperties;
    private String clientSparkVersion;
    private String mainClass;
    @Builder.Default
    private Map<String, String> environmentVariables = new HashMap<>();
    private String action;
    @Builder.Default
    private List<String> appArgs = Collections.emptyList();
}
