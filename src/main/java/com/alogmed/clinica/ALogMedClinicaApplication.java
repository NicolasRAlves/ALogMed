package com.alogmed.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class ALogMedClinicaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ALogMedClinicaApplication.class, args);
	}

}