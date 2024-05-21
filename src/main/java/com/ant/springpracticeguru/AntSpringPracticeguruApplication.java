package com.ant.springpracticeguru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AntSpringPracticeguruApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntSpringPracticeguruApplication.class, args);
	}

}
