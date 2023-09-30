package insight.api.anomalies.dto;

import java.util.List;

import insight.api.anomalies.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
public class ProblemeDTO {
     
	private Long id;
    private String name;
    private List<SolutionDTO> solutions;
	public ProblemeDTO(Long id, String name, List<SolutionDTO> solutions) {
		super();
		this.id = id;
		this.name = name;
		this.solutions = solutions;
	}
 

    
    // Constructors, getters, and setters
}
