package insight.api.anomalies.entity;

import java.security.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor 

public class Historique {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	
	@ManyToOne
	private Client user;
	@ManyToOne
	private Reclamation reclamation;
	private String befor;
	private String after;
	private String dh;
	private String operation;
	
	
	public Historique(Client user, Reclamation reclamation, String befor, String after, String dh, String operation) {
		super();
		this.user = user;
		this.reclamation = reclamation;
		this.befor = befor;
		this.after = after;
		this.dh = dh;
		this.operation = operation;
	}
	
	
}
