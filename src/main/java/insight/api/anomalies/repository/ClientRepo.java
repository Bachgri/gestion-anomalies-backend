package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Client;
 

public interface ClientRepo extends JpaRepository<Client, Long> {
	
	public Client findByName(String un);
	
}											