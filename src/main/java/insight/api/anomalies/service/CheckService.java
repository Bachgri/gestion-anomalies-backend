package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.Check;

public interface CheckService {
	public List<Check> getAll();
	public Check post(Check d);
	public Check put(Check d);
	public Check get(Long id);
	public Check delete(long d);
}
