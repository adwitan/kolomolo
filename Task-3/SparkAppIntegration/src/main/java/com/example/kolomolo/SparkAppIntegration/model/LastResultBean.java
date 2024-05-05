package com.example.kolomolo.SparkAppIntegration.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
public class LastResultBean {
    private List<Cuisine> lastResult = new ArrayList<>();
}
