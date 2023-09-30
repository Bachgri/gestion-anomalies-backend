package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import insight.api.anomalies.entity.*;
import insight.api.anomalies.repository.*;
import insight.api.anomalies.service.*;
 

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	ClientRepo clientRepo;
	RoleRepo roleRepo;
	PasswordEncoder passwordEncoder;
	
	
	public AccountServiceImpl(ClientRepo clientRepo, RoleRepo roleRepo, PasswordEncoder pe) {
		super();
		this.clientRepo = clientRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder=pe;
	}
	
	@Override
	public Client addClient(Client c) { 
		c.setPassword(passwordEncoder.encode(c.getPassword()));
		return clientRepo.saveAndFlush(c);
	}
	
	@Override
	public ClientRole addRole(ClientRole role) { 
		return roleRepo.saveAndFlush(role);
	}

	@Override
	public void addRolToUser(String username, String roleName) {
		Client client = clientRepo.findByName(username);
		ClientRole role = roleRepo.findByRoleName(roleName);
		client.getAppRoles().add(role);
		
	}

	@Override
	public Client loadUserByUserName(String userName) { 
		return clientRepo.findByName(userName);
	}

	@Override
	public List<Client> listClients() { 
		return clientRepo.findAll();
	} 
	@Override
	public Client getOne(Long id) {
		return clientRepo.findById(id).get();
	}

	public Client save(Client user) {
		return clientRepo.save(user);
	}
	@Override
	public List<ClientRole> roles() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}
}
