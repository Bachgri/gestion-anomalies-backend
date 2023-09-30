package  insight.api.anomalies.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reclamation implements Serializable {
	
	
	private static final long serialVersionUID = 7625865710797598422L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Device device;
	@ManyToOne
	private Product product;
	@ManyToOne
	private Probleme probleme; 
	@ManyToOne
	private Solution solution;
	@ManyToOne
	private Reference reference;
		
	private String statut;
	private String description;
	private String date_creation;
	private String date_modification;
	private String imgurl;
	@ManyToOne 
	private Client client;  
	
	public String toString() {
		return ""+id+ ","+device.getName()+","+product.getName()+","+probleme.getName()+","+solution.getName()+","+client.getUsername()+","+device.getVille().getName();
	}
}  