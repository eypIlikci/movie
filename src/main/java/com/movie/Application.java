package com.movie;

import com.movie.entity.*;
import com.movie.entity.enums.Role;
import com.movie.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	private UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setName("TEST-ADMIN");
		user.setEmail("test.admin@test.com");
		user.setPassword("Aa12345");
		user.setLocked(false);
		user.setRole(Role.ADMIN);
		User user2=new User();
		user2.setName("TEST-STANDARD");
		user2.setEmail("test.standard@test.com");
		user2.setPassword("Aa12345");
		user2.setLocked(false);
		user2.setRole(Role.STANDARD);
		userRepository.save(user);
		userRepository.save(user2);
	}
}
