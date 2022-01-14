package vn.insee.retailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = {"vn.insee.jpa"})
@EntityScan(basePackages = {"vn.insee.jpa"})
@ComponentScan(basePackages = "vn.insee")
@SpringBootApplication
@EnableScheduling
public class Main  {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
