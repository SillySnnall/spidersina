package com.silly.spidersina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpidersinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpidersinaApplication.class, args);
	}
}
