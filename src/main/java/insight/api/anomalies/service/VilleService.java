package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.*;

public interface VilleService {
	public List<Ville> getAll();
	public Ville post(Ville p);
	public Ville put(Ville p);
	public Ville get(Long id);
	public Ville delete(long p);
}
