package com.jeancot.naves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NavesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavesApplication.class, args);
	}

}
