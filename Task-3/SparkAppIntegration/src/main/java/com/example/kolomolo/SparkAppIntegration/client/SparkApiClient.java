package com.example.kolomolo.SparkAppIntegration.client;


import com.example.kolomolo.SparkAppIntegration.model.SubmitRequest;
import com.example.kolomolo.SparkAppIntegration.model.SubmitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "spark-api-service",
        url = "${spark-api.urls.base-url}"
)
public interface SparkApiClient {

    @PostMapping(value = "${spark-api.urls.submit-url}", headers = {"Content-Type=application/json"})
    SubmitResponse submit(@RequestBody SubmitRequest chatGPTRequest);
}
