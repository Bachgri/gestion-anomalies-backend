package  insight.api.anomalies.entity;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data		
@Table(name = "devices")
public class Device {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String concatdev;
	private String uniqueid;
	private String name;
	private String lastupdate;
	private boolean disabled;
	private String capteurs;
	private String vehicule;
	private String immatriculation ; 
	private String typeveh;
	private String fonction;
	@ManyToOne
	private Ville ville;
	/*@ManyToMany
    @JoinTable()
    private List<Product> products;*/
	
	public void copyTo(Device d){
		
		this.uniqueid = d.uniqueid;
		this.name = d.name;
		this.lastupdate = d.lastupdate;
		this.disabled = d.disabled;
		this.capteurs = d.capteurs;
		this.vehicule = d.vehicule;
		this.immatriculation = d.immatriculation;
		this.typeveh = d.typeveh;		
		this.fonction = d.fonction;		
		this.ville = d.ville;			
		//this.products = d.products;	
	}									
		
	 	
}
