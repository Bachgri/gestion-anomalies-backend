package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Probleme;



public interface ProblemeRepository extends JpaRepository<Probleme, Long> {

}
