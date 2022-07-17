package com.afry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.afry")
public class TollCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TollCalculatorApplication.class, args);
    }
}
