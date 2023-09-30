package insight.api.anomalies.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper; 

import insight.api.anomalies.entity.*;
import insight.api.anomalies.service.*;
import insight.api.anomalies.serviceImpl.RoleServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@RestController
//@CrossOrigin(originPatterns = "http://*:*")
public class AppRestController {
	
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
	
	
	@GetMapping("/Api/clients") 
	public List<Client> listClients(){
		System.err.println(accs.listClients());
		return accs.listClients();
	}
	@GetMapping("/Api/client/{id}")
	public Client getOne(@PathParam("id") Long id) {
		System.err.println("Client n° " + id + " : " + accs.getOne(id));
		return accs.getOne(id);
	}
	@PostMapping("/Api/clients")
	public Client addClient(@RequestBody Client c) {
		return accs.addClient(c);
	}
	@PostMapping("/roles")
	public ClientRole addRole(@RequestBody ClientRole role) {
		return accs.addRole(role);
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
	@PutMapping("/Api/villes")
	public Ville putVille(@RequestBody Ville v){
		return villeservice.post(v);
	}
	@DeleteMapping("/Api/villes")
	public Ville deleteVille(@RequestBody Ville v){
		return villeservice.post(v);
	}
	/****   Solutions Ops    *****/
	
	@GetMapping("/Api/solutions")
	public List<Solution> solutions(){
		return solserv.getAll();
	}
	@GetMapping("/Api/solutions/{id}")
	public Solution solution(@PathVariable("id") long id){
		return solserv.get(id);
	}
	@PostMapping("/Api/solutions")
	public Solution postSolution(@RequestBody Solution v){
		return solserv.post(v);
	}
	@PutMapping("/Api/solutions")
	public Solution putSolution(@RequestBody Solution v){
		return solserv.post(v);
	}
	@DeleteMapping("/Api/solutions")
	public Solution deleteSolution(@RequestBody Solution v){
		return solserv.post(v);
	}
	
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
	@PutMapping("/Api/devices")
	public Device putDevice(@RequestBody Device v){
		return devserv.post(v);
	}
	@DeleteMapping("/Api/devices")
	public Device deleteDevice(@RequestBody Device v){
		return devserv.post(v);
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
		return recserv.post(v);
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
	
	/********************				problems				**********************/
	@GetMapping("/Api/problemes")
	public List<Probleme> problemes(){
		System.err.println("problemes ***********************");
		return prbserv.getAll();
	}
	@GetMapping("/Api/problemes/{id}")
	public Probleme probleme(@PathVariable("id") Long id){
		return prbserv.get(id);
	}
	@PostMapping("/Api/problemes")
	public Probleme postProbleme(@RequestBody Probleme v){
		return prbserv.post(v);
	}
	@PutMapping("/Api/problemes")
	public Probleme putProbleme(@RequestBody Probleme v){
		return prbserv.post(v);
	}
	@DeleteMapping("/Api/problemes/{id}")
	public Probleme deleteProbleme(@PathVariable("id") Long id){
		return prbserv.delete(id);
	}
	/**********************²			Product Ops			²*************************/

	@GetMapping("/Api/products")
	public List<Product> products(){
		System.err.println("problemes ***********************");
		return prdserv.getAll();
	}
	@GetMapping("/Api/products/{id}")
	public Product product(@PathVariable("id") Long id){
		return prdserv.get(id);
	}
	@PostMapping("/Api/products")
	public Product postProduct(@RequestBody Product v){
		return prdserv.post(v);
	}
	@PutMapping("/Api/products")
	public Product putProduct(@RequestBody Product v){
		return prdserv.post(v);
	}
	@DeleteMapping("/Api/products/{id}")
	public Product deleteProduct(@PathVariable("id") Long id){
		return prdserv.delete(id);
	}
	
	/************************ 		Other operations		 *************************/
	@PostMapping("/addImgToRec")
	public void addImgToRec(@RequestBody ImageRec imageRec) {
		//accs.addRolToUser(roleUser.getUsername(), roleUser.getRoleName());
		recserv.setRecImg(imageRec.getUserid(), imageRec.getImageUrl());
	}
	
	
	
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class RoleUser{
	private String username;
	private String roleName;
}

@Data
@AllArgsConstructor
@NoArgsConstructor 
class ImageRec{
	private long userid;
	private String imageUrl;
}



