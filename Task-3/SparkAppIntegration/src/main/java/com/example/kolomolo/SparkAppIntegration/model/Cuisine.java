package com.example.kolomolo.SparkAppIntegration.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.spark.sql.Row;


@AllArgsConstructor
@Data
public class Cuisine {
    String name;
    int count;

    public static Cuisine mapRowToCuisine(Row row) {
        String name = row.getString(0);
        int count = Integer.parseInt(row.getString(1));
        return new Cuisine(name, count);
    }
}
