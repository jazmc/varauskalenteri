package com.example.varauskalenteri;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.varauskalenteri.web.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests().antMatchers("/css/**", "/add").permitAll() // Enable css when logged out
        .and()
        .authorizeRequests()
          .anyRequest().authenticated()
          .and()
      .formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/varauskalenteri", true)
			.permitAll()
		.and()
			.logout()
			.permitAll();
	}
	/* Nämä oli ne in-memory jutskat
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList();

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user = User
        		.withUsername("user")
        		.password(passwordEncoder.encode("user"))
        		.roles("USER")
        		.build();

        users.add(user);

        user = User
        		.withUsername("admin")
        		.password(passwordEncoder.encode("admin"))
        		.roles("USER", "ADMIN")
        		.build();

    	users.add(user);

        return new InMemoryUserDetailsManager(users);
    }
    */
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	 auth.userDetailsService(userDetailsService).passwordEncoder(new
	 BCryptPasswordEncoder());
	}
	
}