package insight.api.anomalies;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.ClientRole;
import insight.api.anomalies.service.AccountService;

@SpringBootApplication
public class AnomaliesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnomaliesApplication.class, args);
	}

	
	@Bean
	CommandLineRunner start(AccountService fr) {
		return args ->{
			/*ArrayList<ClientRole> roles = new ArrayList<>();
			ClientRole role = new ClientRole("ADMIN");
			fr.addRole(role);
			roles.add(role);
 			fr.addClient(new Client(null, "admin", "admin", "admin15963", "email", "0622115470", "",roles ));*/
		};
	} 
	
	
}
