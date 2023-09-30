package insight.api.anomalies.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.ClientRole;
import insight.api.anomalies.entity.Device;
import insight.api.anomalies.entity.Historique;
import insight.api.anomalies.entity.Probleme;
import insight.api.anomalies.entity.Product;
import insight.api.anomalies.entity.Reclamation;
import insight.api.anomalies.entity.Request;
import insight.api.anomalies.entity.Response;
import insight.api.anomalies.entity.Solution;
import insight.api.anomalies.entity.Ville;
import insight.api.anomalies.exception.DisabledUserException;
import insight.api.anomalies.exception.InvalidUserCredentialsException;
import insight.api.anomalies.jwt.JwtUtil;
import insight.api.anomalies.service.AccountService;
import insight.api.anomalies.service.DeviceService;
import insight.api.anomalies.service.HistoriqueService;
import insight.api.anomalies.service.ProblemeService;
import insight.api.anomalies.service.ProductService;
import insight.api.anomalies.service.ReclamationService;
import insight.api.anomalies.service.SolutionService;
import insight.api.anomalies.service.VilleService;
import insight.api.anomalies.serviceImpl.RoleServiceImpl;
import insight.api.anomalies.serviceImpl.UserAuthService;
import insight.api.anomalies.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin(originPatterns = "http://*:*")
public class JwtRestApi {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	AccountService accs;
	@Autowired
	RoleServiceImpl rserv;
	@Autowired 
	VilleService villeservice;
	@Autowired
	SolutionService solserv;
	@Autowired 
	ReclamationService recserv;
	@Autowired
	ProblemeService prbserv;
	@Autowired
	DeviceService devserv;
	@Autowired
	ProductService prdserv;
	@Autowired
	HistoriqueService hs; 
	
	@PostMapping("/signin")
	public ResponseEntity<Response> generateJwtToken(@RequestBody Request request) throws Exception {
		org.springframework.security.core.Authentication authentication = null;
		System.out.println("resuest : " + request.getUsername());
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (DisabledException e) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}

		User user =  (User) authentication.getPrincipal();
		Set<ClientRole> roles = new HashSet<>();
				
				//((org.springframework.security.core.Authentication) user).getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
		user.getAuthorities().forEach(r->{
			roles.add(new ClientRole(r.getAuthority()));
		});
		String token = jwtUtil.generateToken(authentication);
		Response response = new Response();
		response.setToken(token);
		
		response.setRole(roles.stream().collect(Collectors.toList()).get(0).getRoleName());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Client request) {
		
		userAuthService.saveUser(request);
		
		return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
	}

	
	
	
	@GetMapping("/Api/clients") 
	public List<Client> listClients(){
		System.err.println(accs.listClients());
		return accs.listClients();
	}
	@GetMapping("/Api/clients/{id}")
	public Client getOne(@PathVariable("id") Long id) {
		System.err.println("Client n° " + id + " : " + accs.getOne(id));
		return accs.getOne(id);
	}
	@PostMapping("/Api/clients")
	public Client addClient(@RequestBody Client c) {
		return accs.addClient(c);
	}
	@PostMapping("/Api/roles")
	public ClientRole addRole(@RequestBody ClientRole role) {
		return accs.addRole(role);
	}
	@GetMapping("/Api/roles")
	public List<ClientRole> rolesList(){
		return accs.roles();
	}
	@GetMapping("/roles")
	public List<ClientRole> roles() {
		return rserv.findAll() ;
	}
	@PostMapping("/addRoleToUser")
	public void grantRoleToUser(@RequestBody RoleUser roleUser) {
		accs.addRolToUser(roleUser.getUsername(), roleUser.getRoleName());
	}

	@GetMapping(path = "/refreshToken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse respense) throws IOException {
		System.err.println("----------------------------------------------------------------------");
		String jwtAuthorizationToken = request.getHeader("Authorization"); 
		//System.out.println("refresh token : "+jwtAuthorizationToken);
		if(jwtAuthorizationToken!=null && jwtAuthorizationToken.startsWith("Bearer ")) {
			try {
				
				String jwt = jwtAuthorizationToken.substring(7);
				System.out.println(jwt);
				// parse the jwt 
				Algorithm algo = Algorithm.HMAC256("oualid_bachgri@2001");
				JWTVerifier jwtVerifier = JWT.require(algo).build();
				DecodedJWT jwtDecode =  jwtVerifier.verify(jwt);
				String username = jwtDecode.getSubject();
				//System.out.println("username : " + username);
				// authentiier l'user
				Client user = accs.loadUserByUserName(username);
				//System.err.println("loged username : " + user.getUsername());
				String[] roles = Arrays.copyOf(user.getAppRoles().stream().map(t -> t.getRoleName()).toArray(), user.getAppRoles().size(), String[].class);

				String jwtAccessToken = JWT.create()
					    .withSubject(user.getUsername())
					    .withExpiresAt(new Date(System.currentTimeMillis()+60*1000))
					    .withIssuer(request.getRequestURL().toString())
					    .withArrayClaim("roles",roles)
					    .sign(algo);
				Map<String, String> idToken = new HashMap<>();
				idToken.put("access-token", jwtAccessToken);
				idToken.put("refresh-toekn", jwt);
				idToken.put("role", roles[0]);
				new ObjectMapper().writeValue(respense.getOutputStream(), idToken);
				respense.setContentType("application/json");
				System.out.println("Acces token : "+idToken);	
				
			}catch(Exception e) {
				System.err.println("Problème lors de décodage de JWT *refresh");
				respense.setHeader("Erreur", e.getMessage());
				respense.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}else {
			throw new RuntimeException("Refresh token required *refresh");
		}
	}
	
	/***   ville ops   ***/
		
		@GetMapping("/Api/villes")
		public List<Ville> villes(){
			return villeservice.getAll();
		}
		@GetMapping("/Api/villes/{id}")
		public Ville ville(@PathVariable("id") long id){
			return villeservice.get(id);
		}
		@PostMapping("/Api/villes")
		public Ville postVille(@RequestBody Ville v){
			return villeservice.post(v);
		}
		@PutMapping("/Api/villes/{id}")
		public Ville putVille(@RequestBody Ville v, @PathVariable("id") long id){
			Ville v2 = villeservice.get(id);
			v2.setName(v.getName());
			return villeservice.post(v2);
		}
		@DeleteMapping("/Api/villes/{id}")
		public Ville deleteVille(@PathVariable long id){
			return villeservice.delete(id);
		}
	/****   Solutions Ops    *****/
	
		/*********  Devices Ops  *********/
		@GetMapping("/Api/devices")
		public List<Device> devices(){
			return devserv.getAll();
		}
		@GetMapping("/Api/devices/{id}")
		public Device device(@PathVariable("id") long id){
			return devserv.get(id);
		}
		@PostMapping("/Api/devices")
		public Device postDevice(@RequestBody Device v){
			return devserv.post(v);
		}
		@PutMapping("/Api/devices/{id}")
		public Device putDevice(@PathVariable("id") long id ,@RequestBody Device v){
			Device d = devserv.get(id);	
			v.setId(d.getId());
			return devserv.post(v);		
		}
		@DeleteMapping("/Api/devices/{id}")
		public Device deleteDevice(@PathVariable("id") long v){
			return devserv.delete(v)
;		}
		
		@GetMapping("/Api/devicesmaj")
		public List<Device> maj(){
			return devserv.maj();
		}
	/***********  Reclamation Ops  *************/
		@GetMapping("/Api/reclamations")
		public List<Reclamation> reclamations(){
			System.err.println("reclamations ***********************");
			return recserv.getAll();
		}
		@GetMapping("/Api/reclamations/{id}")
		public Reclamation reclamation(@PathVariable("id") Long id){
			return recserv.get(id);
		}
		@PostMapping("/Api/reclamations")
		public Reclamation postReclamation(@RequestBody Reclamation v){
			System.out.println(v);
			
			
			
				Reclamation rs =  recserv.post(v);
				//hs.add(user, v, rs, "CREATION");
				return rs ;
			
			
		}
		
		
		@PutMapping("/Api/reclamations")
		public Reclamation putReclamation(@RequestBody Reclamation v){
			return recserv.post(v);
		}
		@DeleteMapping("/Api/reclamations/{id}")
		public Reclamation deleteReclamation(@PathVariable("id") Long id){
			return recserv.delete(id);
		}
	
	/**
	 * @throws IOException 
	 * @throws IllegalStateException ***********************************/
		
		@PostMapping("/upload")
		public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("recid") String recid) throws Exception {
			return recserv.upload(file, recid);
		}
		
		@GetMapping("/download/{recid}")
		public ResponseEntity<byte[]> downloadFile(@PathVariable("recid") long recid) {
	        return recserv.download(recid);
	    }
		 
		
		
	/************************ 		Other operations		 *************************/
		@PostMapping("/addImgToRec")
		public void addImgToRec(@RequestBody ImageRec imageRec) {
			//accs.addRolToUser(roleUser.getUsername(), roleUser.getRoleName());
			recserv.setRecImg(imageRec.getUserid(), imageRec.getImageUrl());
		}

	
	
}

