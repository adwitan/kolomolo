package com.example.kolomolo.SparkAppIntegration.controller;


import com.example.kolomolo.SparkAppIntegration.model.Cuisine;
import com.example.kolomolo.SparkAppIntegration.model.SubmitResponse;
import com.example.kolomolo.SparkAppIntegration.service.SparkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class SparkAppIntegrationController {

    private final SparkService sparkService;


    @PostMapping("/api/run")
    public SubmitResponse getCousins() {
        try {
            return sparkService.submitJob(
                    "/opt/TopCuisine-1.0-SNAPSHOT-jar-with-dependencies.jar",
                    "com.example.topcuisine.Spark",
                    "Spark Application"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/sync")
    public List<Cuisine> getResult() {
        try {
            sparkService.syncResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sparkService.getLastResult();
    }

}
