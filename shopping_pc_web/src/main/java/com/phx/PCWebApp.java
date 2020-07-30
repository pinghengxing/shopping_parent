package com.phx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PCWebApp {


    public static void main(String[] args) {
		 SpringApplication.run(PCWebApp.class, args);
	}

}
