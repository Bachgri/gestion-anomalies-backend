package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.*; 

public interface AccountService {
	public Client addClient(Client c);
	public ClientRole addRole(ClientRole role);
	public void addRolToUser(String username, String roleName);
	public Client loadUserByUserName(String userName);
	public List<Client> listClients();
	public Client getOne(Long id);
	public List<ClientRole> roles();
	
} 