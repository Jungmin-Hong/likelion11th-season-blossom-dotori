package season.blossom.dotori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DotoriApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotoriApplication.class, args);
	}

}
