package com.example.bookstore;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;
import com.example.bookstore.domain.User;
import com.example.bookstore.domain.UserRepository;

/*
 * 
 * Sovellus pyörii PostgreSQL:n kanssa osoitteessa https://jazmcbookstore.herokuapp.com/login
 * 
 */

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(BookRepository repository, CategoryRepository catrep, UserRepository urep) {
		return (args) -> {
			
			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "example@something.com", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ex@domain.com", "ADMIN");
			urep.save(user1);
			urep.save(user2);
			
			Category a = new Category("Fantasy");
			Category b = new Category("Historical fantasy");
			Category c = new Category("Comedy");
			Category d = new Category("Drama");

			catrep.save(a);
			catrep.save(b);
			catrep.save(c);
			catrep.save(d);

			Book h = new Book("12345", "Harry Potter ja Viisasten kivi", "J.K. Rowling", "2002", a);
			Book m = new Book("23456", "Muukalainen", "Diana Gabaldon", "2000", b);

			repository.save(h);
			repository.save(m);

		};
	}

}
