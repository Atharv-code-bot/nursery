package com.sakshi.nursery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class NurseryEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NurseryEcommerceApplication.class, args);
	}

}
