package com.example.dataflowsrevice.utilities;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FakerGen {
    @Bean
    public Faker faker() {
        return new Faker(new Locale(System.getenv("LOCAL")));
    }

}
