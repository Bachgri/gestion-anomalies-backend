package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.Historique;
import insight.api.anomalies.entity.Reclamation;
import insight.api.anomalies.repository.HistoriqueRepository;
import insight.api.anomalies.service.HistoriqueService;
import insight.api.anomalies.utils.Utils;

@Service
public class HistoriqueServiceImpl implements HistoriqueService {

	HistoriqueRepository historiqueRepository;
	
	
	
	@Override
	public Historique add(Client user, Reclamation v, String rs, String string) {
		Historique h = new Historique();
		h.setUser(user);
		h.setReclamation(v);
		h.setBefor("befor");
		h.setAfter(v.toString());
		h.setDh(Utils.currentTimeStamp());
		h.setOperation("CREATION" );
		
		System.err.println(h);
		return post(h);
		
	}
	
	public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository) {
		super();
		this.historiqueRepository = historiqueRepository;
	}

	@Override
	public List<Historique> getAll() { 
		return historiqueRepository.findAll();
	}

	@Override
	public Historique post(Historique d) { 
		return historiqueRepository.save(d);
	}

	@Override
	public Historique put(Historique d) { 
		return historiqueRepository.save(d);
	}

	@Override
	public Historique get(Long id) { 
		return historiqueRepository.findById(id).get();
	}

	@Override
	public Historique delete(long d) { 
		Historique h = historiqueRepository.findById(d).get();
		historiqueRepository.delete(h);
		return h;
	}

}
