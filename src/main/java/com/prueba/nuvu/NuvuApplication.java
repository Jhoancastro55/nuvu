package com.prueba.nuvu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prueba.nuvu.security.NuvuAuthorizacionFilter;

@SpringBootApplication
public class NuvuApplication {

	public static void main(String[] args) {
		SpringApplication.run(NuvuApplication.class, args);
	}
	
	/*
	 * Configuración para la autenticación del token JWT 
	 */
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter{
		
		@Override
		protected void configure(HttpSecurity security) throws Exception{
			security.csrf().disable()
				.addFilterAfter(new NuvuAuthorizacionFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/hello").permitAll()
				.antMatchers(HttpMethod.GET, "/listarPeople").permitAll()
				.antMatchers(HttpMethod.POST, "/registerPeople").permitAll()
				.antMatchers(HttpMethod.PUT, "/editarPeople").permitAll()
				.anyRequest().authenticated();
		}
	}
}
