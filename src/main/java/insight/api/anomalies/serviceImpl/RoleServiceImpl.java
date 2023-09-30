package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.ClientRole;
import insight.api.anomalies.repository.RoleRepo;
import insight.api.anomalies.service.*;
 

@Service
public class RoleServiceImpl implements RoleService {
	RoleRepo rolerepo;

	public RoleServiceImpl(RoleRepo rolerepo) { 
		this.rolerepo = rolerepo;
	}
	@Override
	public List<ClientRole> findAll(){
		return rolerepo.findAll();
	}
	
}
