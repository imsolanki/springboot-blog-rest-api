package com.springboot.blog.springbootblogrestapi;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="Spring Boot Blog App REST API's",
				description = "Spring Boot Blog Application",
				version = "v1.0",
				contact = @Contact(
						name = "Lalit Solanki",
						email = "shobhitsingh.e28@gmail.com",
						url="https://www.linkedin.com/in/lalit-kumar-singh-aa447451/"
				),
				license = @License(
						name = "Apache 2.0"
						//url = "https://github.com/imsolanki"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/imsolanki"
		)
)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	@Autowired
	private RoleRepository roleRepository;


	@Override
	public void run(String... args) throws Exception {
		Role adminRole=new Role();
		adminRole.setName("ADMIN_ROLE");
		roleRepository.save(adminRole);

		Role userRole =new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}
}
