package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.ClientRole;
 

public interface RoleRepo extends JpaRepository<ClientRole, Long> {
	public ClientRole findByRoleName(String rn);
	
}
 