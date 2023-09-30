package insight.api.anomalies.controller;

import insight.api.anomalies.dto.ProblemeDTO;
import insight.api.anomalies.dto.SolutionDTO;
import insight.api.anomalies.entity.Solution;
import insight.api.anomalies.service.SolutionService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Api/solutions")
@CrossOrigin(originPatterns = "http://*:*")
public class SolutionController {
    private final SolutionService solutionService;

    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    } 

    @GetMapping
    public ResponseEntity<List<SolutionDTO>> getAllSolutions() {
        List<Solution> solutions = solutionService.getAll();
        List<SolutionDTO> solutionDTOs = solutions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(solutionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> getSolutionById(@PathVariable Long id) {
        Solution solution = solutionService.get(id);
        SolutionDTO solutionDTO = convertToDTO(solution);
        return ResponseEntity.ok(solutionDTO);
    }

    @PostMapping
    public ResponseEntity<SolutionDTO> createSolution(@RequestBody SolutionDTO solutionDTO) {
        Solution solution = convertToEntity(solutionDTO);
        Solution createdSolution = solutionService.post(solution);
        SolutionDTO createdSolutionDTO = convertToDTO(createdSolution);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSolutionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolutionDTO> updateSolution(
            @PathVariable Long id,
            @RequestBody SolutionDTO solutionDTO
    ) {
        Solution existingSolution = solutionService.get(id);
        Solution updatedSolution = convertToEntity(solutionDTO);

        // Update the existing solution with the data from the updated solution
        existingSolution.setName(updatedSolution.getName());
        Solution savedSolution = solutionService.put(existingSolution);
        SolutionDTO savedSolutionDTO = convertToDTO(savedSolution);
        return ResponseEntity.ok(savedSolutionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SolutionDTO> deleteSolution(@PathVariable Long id) {
    	SolutionDTO pdto = convertToDTO(solutionService.get(id));
    	System.err.println("delete " + pdto);
        solutionService.delete(id);
        return ResponseEntity.ok(pdto);
    }

    private SolutionDTO convertToDTO(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        System.out.println(solution);
        solutionDTO.setId(solution.getId());
        solutionDTO.setName(solution.getName());
 //       BeanUtils.copyProperties(solution, solutionDTO);
        return solutionDTO;
    }

    private Solution convertToEntity(SolutionDTO solutionDTO) {
        Solution solution = new Solution();
        BeanUtils.copyProperties(solutionDTO, solution);
        return solution;
    }
}
