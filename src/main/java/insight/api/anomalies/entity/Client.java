package insight.api.anomalies.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor 
@ToString
@Setter
public class Client implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true) 
	private String name;
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	String password;
	private String email;
	private String phone; 
	private String adress;
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<ClientRole> appRoles = new ArrayList<>();
	@Column(nullable =  true)
	double latitude;
	@Column(nullable = true)
	double longitude;
	
	public Client(){}
	
	public Client(String nom, String username, String password, String email, String phone, String adress,
			Collection<ClientRole> appRoles) {
		super();
		this.name = nom;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.adress = adress;
		this.appRoles = appRoles;
	}

	public Client(String nom, String email, String phone, String adress) {
		super();
		this.name = nom;
		this.email = email;
		this.phone = phone;
		this.adress = adress;
	}
	

	
	
	
	
}
