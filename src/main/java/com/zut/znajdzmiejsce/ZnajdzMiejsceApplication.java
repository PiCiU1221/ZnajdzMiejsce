package com.zut.znajdzmiejsce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZnajdzMiejsceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZnajdzMiejsceApplication.class, args);
	}

}
