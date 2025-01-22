package com.dev.stockmarketsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dev.stockmarketsystem.models.*;
import com.dev.stockmarketsystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockmarketsystemApplication  {

	public static void main(String[] args)  {
		SpringApplication.run(StockmarketsystemApplication.class, args);
	}


}
