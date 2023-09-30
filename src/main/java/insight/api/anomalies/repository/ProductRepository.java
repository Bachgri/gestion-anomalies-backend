package insight.api.anomalies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import insight.api.anomalies.entity.Product;



public interface ProductRepository extends JpaRepository<Product, Long> {
	
}	
