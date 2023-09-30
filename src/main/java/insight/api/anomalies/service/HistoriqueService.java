package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.Client;
import insight.api.anomalies.entity.Historique;
import insight.api.anomalies.entity.Reclamation;

public interface HistoriqueService {
	public List<Historique> getAll();
	public Historique post(Historique d);
	public Historique put(Historique d);
	public Historique get(Long id);
	public Historique delete(long d);
	public Historique add(Client user, Reclamation v, String string2, String string);
}
