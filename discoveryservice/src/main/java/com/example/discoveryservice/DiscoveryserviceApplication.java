package com.example.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer     //SpringBoot 프로젝트가 Eureka Server 역할을 하기 위해 Server의 자격으로 등록하기 위한 어노테이션
public class DiscoveryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryserviceApplication.class, args);
	}

}
