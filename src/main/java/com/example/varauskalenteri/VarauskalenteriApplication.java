package com.example.varauskalenteri;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.varauskalenteri.domain.Varaus;
import com.example.varauskalenteri.domain.VarausRepository;
import com.example.varauskalenteri.domain.Kategoria;
import com.example.varauskalenteri.domain.KategoriaRepository;
import com.example.varauskalenteri.domain.User;
import com.example.varauskalenteri.domain.UserRepository;

/*
 * 
 * Tähän kommentti kun deployattu
 * 
 */

@SpringBootApplication
public class VarauskalenteriApplication {

	public static void main(String[] args) {
		SpringApplication.run(VarauskalenteriApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(VarausRepository repository, KategoriaRepository catrep, UserRepository urep) {
		return (args) -> {
			
			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "example@something.com", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ex@domain.com", "ADMIN");
			urep.save(user1);
			urep.save(user2);
			
			Kategoria a = new Kategoria("Fantasy");
			Kategoria b = new Kategoria("Historical fantasy");
			Kategoria c = new Kategoria("Comedy");
			Kategoria d = new Kategoria("Drama");

			catrep.save(a);
			catrep.save(b);
			catrep.save(c);
			catrep.save(d);

			Varaus h = new Varaus("12345", "Harry Potter ja Viisasten kivi", "J.K. Rowling", "2002", a);
			Varaus m = new Varaus("23456", "Muukalainen", "Diana Gabaldon", "2000", b);

			repository.save(h);
			repository.save(m);

		};
	}

}
