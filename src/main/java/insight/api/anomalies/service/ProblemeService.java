package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.*;

public interface ProblemeService {
	public List<Probleme> getAll();
	public Probleme post(Probleme p);
	public Probleme put(Probleme p);
	public Probleme get(Long id);
	public Probleme delete(long p);
}
