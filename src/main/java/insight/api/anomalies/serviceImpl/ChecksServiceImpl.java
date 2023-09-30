package insight.api.anomalies.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import insight.api.anomalies.entity.Check;
import insight.api.anomalies.repository.ChecksRepository;
import insight.api.anomalies.service.CheckService;

@Service
public class ChecksServiceImpl implements CheckService {

	ChecksRepository checksRepository;
	
	
	public ChecksServiceImpl(ChecksRepository checksRepository) { 
		this.checksRepository = checksRepository;
	}

	@Override
	public List<Check> getAll() { 
		return checksRepository.findAll();
	}

	@Override
	public Check post(Check d) { 
		return checksRepository.save(d);
	}

	@Override
	public Check put(Check d) {  
		return checksRepository.save(d);
	}

	@Override
	public Check get(Long id) { 
		return checksRepository.findById(id).get();
	}

	@Override
	public Check delete(long d) { 
		Check c = checksRepository.findById(d).get();
		checksRepository.delete(c);
		return c;
	}

}
