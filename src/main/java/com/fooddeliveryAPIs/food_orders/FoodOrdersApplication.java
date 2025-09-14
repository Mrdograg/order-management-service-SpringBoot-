package com.fooddeliveryAPIs.food_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodOrdersApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodOrdersApplication.class, args);
	}
}
