package com.apollo.pseudoSyncEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PseudoSyncEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PseudoSyncEngineApplication.class, args);
	}

}
