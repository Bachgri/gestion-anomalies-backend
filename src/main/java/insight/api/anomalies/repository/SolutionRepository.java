package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Solution;



public interface SolutionRepository extends JpaRepository<Solution, Long> {

}
