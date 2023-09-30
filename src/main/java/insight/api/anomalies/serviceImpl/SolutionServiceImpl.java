package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Solution;
import insight.api.anomalies.repository.SolutionRepository;
import insight.api.anomalies.service.SolutionService;

 
@Service
public class SolutionServiceImpl implements SolutionService {
	
	@Autowired
	SolutionRepository solrep;
	@Override
	public List<Solution> getAll() { 
		return solrep.findAll();
	}

	@Override
	public Solution post(Solution p) { 
		return solrep.save(p);
	}

	@Override
	public Solution put(Solution p) { 
		return solrep.save(p);
	}

	@Override
	public Solution get(Long id) { 
		return solrep.findById(id).get();
	}

	@Override
	public Solution delete(long p) {
		Solution s = solrep.findById(p).get();
		solrep.delete(s);
		return s;
	}

}
