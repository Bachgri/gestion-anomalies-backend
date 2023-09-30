package insight.api.anomalies.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Probleme;
import insight.api.anomalies.entity.Product;
import insight.api.anomalies.repository.ProductRepository;
import insight.api.anomalies.service.ProductService;

 
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Override
	public List<Product> getAll() { 
		return productRepository.findAll();
	}

	@Override
	public Product post(Product p) { 
		return productRepository.save(p);
	}

	@Override
	public Product put(Product p) {
		System.out.println("put product :" + p);
		return productRepository.save(p);
	}

	@Override
	public Product get(Long id) { 
		return productRepository.findById(id).get();
	}

	@Override
	public Product delete(long p) {
		Product pp = productRepository.findById(p).get();
		productRepository.delete(pp);
		return pp;
	}
	@Override
	public void deleteProboleme(long pid, long id) {
		Product p = productRepository.getById(pid);
		List<Probleme> old = p.getProblemes(), New = new ArrayList<>();
		System.err.println("old : " + old);
		for (Probleme probleme : old) {
			if(probleme.getId() != id)
				New.add(probleme);
		}
		System.err.println("new : " + New);
		p.setProblemes(New);
		productRepository.save(p);
	}

}
