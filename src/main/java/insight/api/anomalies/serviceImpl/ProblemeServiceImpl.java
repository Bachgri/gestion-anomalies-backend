package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Probleme;
import insight.api.anomalies.repository.ProblemeRepository;
import insight.api.anomalies.service.ProblemeService;

@Service
public class ProblemeServiceImpl implements ProblemeService {

	@Autowired
	ProblemeRepository problemeRepository;
	
	
	@Override
	public List<Probleme> getAll() { 
		return problemeRepository.findAll();
	}

	@Override
	public Probleme post(Probleme p) { 
		return problemeRepository.save(p);
	}

	@Override
	public Probleme put(Probleme p) { 
		return problemeRepository.saveAndFlush(p);
	}

	@Override
	public Probleme get(Long id) { 
		return problemeRepository.findById(id).get();
	}

	@Override
	public Probleme delete(long p) { 
		Probleme pp = problemeRepository.findById(p).get();
		problemeRepository.delete(pp);
		return pp;
	}

}
