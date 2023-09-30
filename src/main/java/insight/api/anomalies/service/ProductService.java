package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.*;

public interface ProductService {
	public List<Product> getAll();
	public Product post(Product p);
	public Product put(Product p);
	public Product get(Long id);
	public Product delete(long p);
	public void deleteProboleme(long pid, long id);
}
