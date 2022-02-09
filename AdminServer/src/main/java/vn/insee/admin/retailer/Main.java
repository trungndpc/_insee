package vn.insee.admin.retailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = "vn.insee.admin")
@EnableJpaRepositories(basePackages = {"vn.insee.jpa"})
@EntityScan(basePackages = {"vn.insee.jpa"})
@SpringBootApplication
@EnableScheduling
public class Main  {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
