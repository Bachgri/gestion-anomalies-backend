package insight.api.anomalies.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import insight.api.anomalies.dto.ProblemeDTO;
import insight.api.anomalies.dto.SolutionDTO;
import insight.api.anomalies.entity.Probleme;
import insight.api.anomalies.entity.Product;
import insight.api.anomalies.entity.Solution;
import insight.api.anomalies.service.ProblemeService;
import insight.api.anomalies.service.ProductService;
import insight.api.anomalies.service.SolutionService;

@RestController 
@RequestMapping("/Api/problemes")
@CrossOrigin(originPatterns = "http://*:*")
public class ProblemeController {
	private static final int ArrayList = 0;
	@Autowired
    private ProblemeService problemeService;
	@Autowired
	SolutionService solutionService;
	@Autowired
	ProductService productService;
	
    // Inject ProblemeService through constructor
		
	 	@PostMapping
	    public ResponseEntity<ProblemeDTO> createProbleme(@RequestBody Probleme probleme) {
	 		System.err.println("insert probleme : " + probleme);
	        //Probleme probleme = mapDTOToProbleme(problemeDTO);
	        Probleme createdProbleme = problemeService.post(probleme);
	        ProblemeDTO createdProblemeDTO = mapProblemeToDTO(createdProbleme);
	        return ResponseEntity.ok(createdProblemeDTO);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<ProblemeDTO> updateProbleme(@RequestBody Probleme problemeDTO, @PathVariable("id") long id) {
	    	System.err.println("probleme id " + id);
	    	Probleme ptoupdate = problemeService.get(id);
	    	System.err.println("you want edit " + ptoupdate + " to " + problemeDTO);
	    	//Probleme temp = mapDTOToProbleme(problemeDTO);
	    	ptoupdate.setName(problemeDTO.getName());
	    	ptoupdate.setSolutions(problemeDTO.getSolutions());
	    	System.err.println("new probleme version : " + ptoupdate);
	    	List<Solution> solutions = problemeDTO.getSolutions();
	        List<Solution> updatedSolutions = new ArrayList<>();
	        System.err.println("solutions to update : ");
	        for (Solution solution : solutions) {
	            long solutionId = solution.getId(); 
	            System.err.println("solution " + solutionId);
	            Solution fetchedSolution = solutionService.get(solutionId);
	            updatedSolutions.add(fetchedSolution);
	        }
	        ptoupdate.setSolutions(updatedSolutions);

	    	problemeService.put(ptoupdate);
	    	return ResponseEntity.ok(mapProblemeToDTO(ptoupdate));
	    	/*
	        //Probleme probleme = mapDTOToProbleme(problemeDTO);
	        //probleme.setId(problemeDTO.getId());
	        Probleme updatedProbleme = problemeService.put(problemeDTO); 
	        System.err.println("Updated probleme : " + updatedProbleme);
	        ProblemeDTO updatedProblemeDTO = mapProblemeToDTO(updatedProbleme);
	        return ResponseEntity.ok(updatedProblemeDTO);*/
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ProblemeDTO> deleteProbleme(@PathVariable Long id) {
	        Probleme p = problemeService.get(id),
	        		ptd = problemeService.get(id);
	        		;
    		ProblemeDTO pp = mapProblemeToDTO(p);
    		System.err.println("you want delete probleme " + id +" = " + ptd);
    		ptd.setSolutions(new ArrayList());
	        /*for (Product product : ptd.getProducts()) {
				productService.deleteProboleme( product.getId(), id);
			}*/
	        System.err.println("Deleting solutions for probleme : " + ptd);
    		
    		System.err.println("saving new probleme version : " + problemeService.post(ptd));
	        
	         //new ProblemeDTO(p.getId(), p.getName(), p.getSolutions());
	        System.err.println("deleted : " + problemeService.delete(id));
	        return ResponseEntity.ok(pp);
	    }


	    @GetMapping("/{id}")
	    public ResponseEntity<ProblemeDTO> getProblemeById(@PathVariable Long id) {
	        Probleme probleme = problemeService.get(id);
	        ProblemeDTO problemeDTO = mapProblemeToDTO(probleme);
	        
	        return ResponseEntity.ok(problemeDTO);
	    }

	    @GetMapping
	    public ResponseEntity<List<ProblemeDTO>> getAllProblemes() {
	        List<Probleme> problemes = problemeService.getAll();
	        List<ProblemeDTO> problemeDTOs = problemes.stream()
	                .map(this::mapProblemeToDTO)
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(problemeDTOs);
	    }

	    private Probleme mapDTOToProbleme(ProblemeDTO problemeDTO) {
	        Probleme probleme = new Probleme();
	        probleme.setId(problemeDTO.getId());
	        probleme.setName(problemeDTO.getName());

	        // Map the solutions (if required)
	        if (problemeDTO.getSolutions() != null) {
	            List<Solution> solutions = new ArrayList<>();

	            for (SolutionDTO solutionDTO : problemeDTO.getSolutions()) {
	                Solution solution = new Solution();
	                solution.setId(solutionDTO.getId());
	                solution.setName(solutionDTO.getName());
	                // Map other fields of the Solution class if needed

	                solutions.add(solution);
	            }

	            probleme.setSolutions(solutions);
	        }

	        return probleme;
	    }

	    private ProblemeDTO mapProblemeToDTO(Probleme probleme) {
	        ProblemeDTO problemeDTO = new ProblemeDTO();
	        System.err.println(probleme);
	        problemeDTO.setId(probleme.getId());
	        problemeDTO.setName(probleme.getName());

	        // Fetch and map the solutions (if required)
	        if(probleme.getSolutions() != null) {
	        	List<SolutionDTO> solutionDTOs = probleme.getSolutions()
	        			.stream()
	        			.map(this::mapSolutionToDTO)
	        			.collect(Collectors.toList());
	        	problemeDTO.setSolutions(solutionDTOs);
	        	
	        }else
	        	probleme.setSolutions(new ArrayList());

	        return problemeDTO;
	    }

	    private SolutionDTO mapSolutionToDTO(Solution solution) {
	        SolutionDTO solutionDTO = new SolutionDTO();
	        solutionDTO.setId(solution.getId());
	        solutionDTO.setName(solution.getName());
	        // Map other solution fields if necessary
	        return solutionDTO;
	    }
}
