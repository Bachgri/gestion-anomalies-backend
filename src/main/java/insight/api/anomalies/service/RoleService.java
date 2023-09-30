package insight.api.anomalies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.ClientRole;

 
@Service
public interface RoleService {
	List<ClientRole> findAll();
}
