package pl.kurs.java.firstspringapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    private boolean success = false;
    private int timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
