package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

}
