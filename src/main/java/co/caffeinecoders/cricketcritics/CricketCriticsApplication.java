package co.caffeinecoders.cricketcritics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //not the best method disabling the default Secuirity Config
public class CricketCriticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CricketCriticsApplication.class, args);
	}

}
