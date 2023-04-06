package com.evgenys.online.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication//он тоже конфиг
@PropertySource("classpath:secured.properties")//конфигу указываем что secured тоже является проперти файлом
public class OnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApplication.class, args);
	}

}


