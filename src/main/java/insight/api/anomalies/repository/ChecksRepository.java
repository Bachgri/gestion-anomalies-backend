package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Check;

public interface ChecksRepository extends JpaRepository<Check, Long> {

}
