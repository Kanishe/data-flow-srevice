package com.example.dataflowsrevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@SpringBootApplication
public class DataFlowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataFlowServiceApplication.class, args);
    }

}
