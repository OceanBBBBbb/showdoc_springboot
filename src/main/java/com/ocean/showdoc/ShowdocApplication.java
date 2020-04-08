package com.ocean.showdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class ShowdocApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowdocApplication.class, args);
	}

}
