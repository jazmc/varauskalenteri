package com.example.varauskalenteri;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
			User user1 = new User("user", "Ilpo", "Ilmailuautisti", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "example@something.com", "USER");
			User user2 = new User("admin", "Hannu", "Hallinnoija", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ex@domain.com", "ADMIN");
			User user3 = new User("user1", "Esko", "Esimerkkilentäjä", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "example@something.com", "USER");
			User user4 = new User("user2", "Leevi", "Lentokonefani", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "example@something.com", "USER");
			urep.save(user1);
			urep.save(user2);
			urep.save(user3);
			urep.save(user4);
			
			Kategoria a = new Kategoria("Lentovaraus");
			Kategoria b = new Kategoria("Huolto");
			
			catrep.save(a);
			catrep.save(b);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			Varaus h = new Varaus(LocalDateTime.parse("2021-11-20 12:30", formatter), LocalDateTime.parse("2021-11-20 15:30", formatter), "varaaja", "selitys", a);
			Varaus m = new Varaus(LocalDateTime.parse("2021-11-22 16:00", formatter), LocalDateTime.parse("2021-11-25 19:30", formatter), "varaaja", "selitys", b);
			Varaus n = new Varaus(LocalDateTime.parse("2021-11-15 07:30", formatter), LocalDateTime.parse("2021-11-15 12:30", formatter), "aamuvirkku", "selitys", a);
			Varaus o = new Varaus(LocalDateTime.parse("2021-11-14 14:00", formatter), LocalDateTime.parse("2021-11-14 15:00", formatter), "pikalentäjä", "selitys", a);
			Varaus p = new Varaus(LocalDateTime.parse("2021-11-17 14:00", formatter), LocalDateTime.parse("2021-11-17 18:00", formatter), "pikalentäjä", "selitys", a);
			Varaus q = new Varaus(LocalDateTime.parse("2021-11-21 10:00", formatter), LocalDateTime.parse("2021-11-21 13:30", formatter), "pikalentäjä", "selitys", a);
			Varaus r = new Varaus(LocalDateTime.parse("2021-11-08 17:00", formatter), LocalDateTime.parse("2021-11-08 21:00", formatter), "pikalentäjä", "selitys", a);
			
			h.setUser(user2);
			m.setUser(user1);
			n.setUser(user1);
			o.setUser(user3);
			p.setUser(user3);
			q.setUser(user4);
			r.setUser(user2);
			
			repository.save(h);
			repository.save(m);
			repository.save(n);
			repository.save(o);
			repository.save(p);
			repository.save(q);
			repository.save(r);

		};
	}

}
