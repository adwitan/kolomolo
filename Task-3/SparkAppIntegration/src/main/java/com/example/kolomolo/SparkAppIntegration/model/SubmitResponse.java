package com.example.kolomolo.SparkAppIntegration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitResponse {
    private String action;
    private String message;
    private String serverSparkVersion;
    private String submissionId;
    private boolean success;
}
