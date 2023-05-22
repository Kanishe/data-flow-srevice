package com.example.dataflowsrevice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class DataFlowSreviceApplicationTests {


    @Test
    void contextLoads() {
    }


    @Test
    void currdate(){
        String data = "28.10.2023";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(data,dateTimeFormatter);
       Assertions.assertEquals(localDate.getDayOfMonth(),28);
    }

}
