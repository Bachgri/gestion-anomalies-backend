package insight.api.anomalies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.algorithms.Algorithm;

import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.Historique;
import insight.api.anomalies.entity.Reclamation;
import insight.api.anomalies.service.AccountService;
import insight.api.anomalies.service.HistoriqueService;
import insight.api.anomalies.service.ReclamationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController 
@RequestMapping("/Api/log")
@CrossOrigin(originPatterns = "http://*:*")
public class LogController {

	@Autowired
	AccountService accs;

	@Autowired
	HistoriqueService hs; 

	@Autowired 
	ReclamationService recserv;
	
	@GetMapping
	public ResponseEntity<List<Historique>> logs(){
		return ResponseEntity.ok(hs.getAll());
	}
	
	@PostMapping 
	public ResponseEntity<Historique> addLog(@RequestBody HistoriqueForm form, @RequestHeader("Authorization") String authorization ){
		String jwt = authorization.replace("HTTP_TOKEN ", "");
		System.err.println(jwt);
		Algorithm algo;
		try {
			/*algo = Algorithm.HMAC256("oualid_bachgri@2001");
			JWTVerifier jwtVerifier = JWT.require(algo).build();
			DecodedJWT jwtDecode =  jwtVerifier.verify(jwt);
			String username = jwtDecode.getSubject();
			//System.out.println("username : " + username);
			// authentiier l'user*/
			Claims body = Jwts.parser().setSigningKey("Oualid@insight2023").parseClaimsJws(jwt).getBody();
			Client user = accs.loadUserByUserName(body.getSubject());
			System.err.println(user);
			return ResponseEntity.ok(hs.add(user, recserv.get(form.getId_rec()), form.getOld().toString(), form.getOp()));
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
	}
}

@Data @AllArgsConstructor @NoArgsConstructor

class HistoriqueForm{
	private Long id_rec;
	private String op;
	private Reclamation old;
}
