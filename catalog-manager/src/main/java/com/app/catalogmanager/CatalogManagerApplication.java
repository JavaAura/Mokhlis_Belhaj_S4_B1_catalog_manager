package com.app.catalogmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.catalogmanager.Entity.Role;
import com.app.catalogmanager.Repository.RoleRepository;

@SpringBootApplication
public class CatalogManagerApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CatalogManagerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunnerRole() {
		return args -> {
			Role role = new Role();
			role.setName("USER");
			if(!roleRepository.findByName("USER").isPresent()) {
				roleRepository.save(role);
			}
			Role admin = new Role();
			admin.setName("ADMIN");
			if(!roleRepository.findByName("ADMIN").isPresent()) {
				roleRepository.save(admin);
			}
			
		};


		
	}

}
