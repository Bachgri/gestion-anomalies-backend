package  insight.api.anomalies.entity;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data	
@Table(name = "problemes")
public class Probleme {   
										
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToMany(cascade = CascadeType.DETACH)
   /* @JoinTable(name = "probleme_solution",
            joinColumns = @JoinColumn(name = "probleme_id"),
            inverseJoinColumns = @JoinColumn(name = "solution_id"))/*/
    private List<Solution> solutions;
    
    //@JsonProperty(access = Access.WRITE_ONLY) 
   /* @ManyToMany
    @JoinTable(name = "probleme_product")
    private List<Product> products;*/
}