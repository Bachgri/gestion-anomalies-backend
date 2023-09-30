package insight.api.anomalies.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.ClientRole;
import insight.api.anomalies.entity.Request;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountServiceImpl userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client user = userRepository.loadUserByUserName(username);
		System.err.println("UserAyhtService : 36 : user : " + username + " : "  + user);
		List<ClientRole> userRoles = user.getAppRoles().stream().collect(Collectors.toList());
/*
		List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRole());
		}).collect(Collectors.toList());*/
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		/*userRoles.stream().map(r ->{
			//return new SimpleGrantedAuthority(r.getRoleName());
		});*/
		userRoles.forEach(r->{
			grantedAuthorities.add(new SimpleGrantedAuthority(r.getRoleName()));
			
		});
		return new User(username, user.getPassword(), grantedAuthorities);
		//return new org.springframework.security.core.userdetails.User(username, user.getAppRoles(), grantedAuthorities);
	}
	
	public void saveUser(Client request) {
		if (userRepository.loadUserByUserName(request.getUsername()) != null) {
			throw new RuntimeException("User already exists");
		}
		Client user = new Client();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setAppRoles(request.getAppRoles().stream().map(r -> {
			ClientRole ur = new ClientRole();
			ur.setRoleName(r.getRoleName());
			return ur;
		}).collect(Collectors.toSet()));
		userRepository.save(user);
	}
	
}	