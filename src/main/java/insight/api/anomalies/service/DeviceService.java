package insight.api.anomalies.service;

import java.util.List;

import insight.api.anomalies.entity.Device;



public interface DeviceService {
	public List<Device> getAll();
	public Device post(Device d);
	public Device put(Device d);
	public Device get(Long id);
	public Device delete(long d);
	public List<Device> maj(); 
}
