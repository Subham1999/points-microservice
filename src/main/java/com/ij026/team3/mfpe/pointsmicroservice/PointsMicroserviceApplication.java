package com.ij026.team3.mfpe.pointsmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PointsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointsMicroserviceApplication.class, args);
	}

}
