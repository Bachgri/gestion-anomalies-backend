package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.*;

public interface SolutionService {
	public List<Solution> getAll();
	public Solution post(Solution p);
	public Solution put(Solution p);
	public Solution get(Long id);
	public Solution delete(long p);
}
