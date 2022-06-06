package com.example.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class Service1Application {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @GetMapping("/service-first")
    public String get() {
        return "Service 1";
    }

    @GetMapping("/service-first/service-second")
    public String cekService(){
        String info = restTemplate.getForObject("lb://service-second/service-second",String.class);
        assert info != null;
        return info.toUpperCase(Locale.ROOT);
    }
}
