package com.example.kolomolo.SparkAppIntegration.view;

import com.example.kolomolo.SparkAppIntegration.service.SparkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    public static final String CUISINE_LIST = "cuisineList";
    private final SparkService sparkService;

    @GetMapping("/result")
    public String getResultFromHdfs(Model model) {
        model.addAttribute(CUISINE_LIST, sparkService.getLastResult());
        return "result";
    }

    @PostMapping("/start")
    public String sparkCalc(Model model) {
        model.addAttribute(CUISINE_LIST, sparkService.getLastResult());
        sparkService.submitJob(
                "/opt/TopCuisine-1.0-SNAPSHOT-jar-with-dependencies.jar",
                "com.example.topcuisine.Spark",
                "Spark Application"
        );
        return "result";
    }

    @PostMapping("/sync")
    public String syncWithHdfs(Model model) throws Exception {
        model.addAttribute(CUISINE_LIST, sparkService.getLastResult());
        sparkService.syncResult();
        return "result";
    }
}
