package com.nexus;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class NexusServicesApplication {

	public static void main(String[] args) {



		SpringApplication.run(NexusServicesApplication.class, args);
	}


}