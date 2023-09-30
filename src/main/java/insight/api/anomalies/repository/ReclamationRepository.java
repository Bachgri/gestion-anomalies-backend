package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Reclamation;


public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

}
