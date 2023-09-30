package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Ville;
import insight.api.anomalies.repository.VilleRepository;
import insight.api.anomalies.service.VilleService;



@Service
public class VilleServiceImpl implements VilleService {

	@Autowired 
	VilleRepository villerep;
	
	@Override
	public List<Ville> getAll() { 
		return villerep.findAll();
	}
	
	@Override
	public Ville post(Ville p) {
		return villerep.save(p);
	}
	
	@Override
	public Ville put(Ville p) {
		return null;
	}
	
	@Override
	public Ville get(Long id) {
		return villerep.findById(id).get();
	}
	
	@Override
	public Ville delete(long p) { 
		Ville ville = villerep.findById(p).get();
		villerep.delete(ville);
		return ville;
	}
	
}
