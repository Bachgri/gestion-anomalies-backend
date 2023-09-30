package insight.api.anomalies.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.http.HttpClient;
import java.net.http.*;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import insight.api.anomalies.entity.Device;
import insight.api.anomalies.entity.DeviceFDB;
import insight.api.anomalies.repository.DeviceRepository;
import insight.api.anomalies.repository.VilleRepository;
import insight.api.anomalies.service.DeviceService;  
 
@Service
public class DeviceServiceImpl implements DeviceService {

	DeviceRepository deviceRepository;
	VilleRepository villeRepo ;
	
	public DeviceServiceImpl(DeviceRepository deviceRepository, VilleRepository villeRepo) {
		this.deviceRepository = deviceRepository;
		this.villeRepo= villeRepo;
	}

	@Override
	public List<Device> getAll() {
		return deviceRepository.findAll();
	}

	@Override
	public Device post(Device d) {
		return deviceRepository.save(d);
	}

	@Override
	public Device put(Device d) {
		Device dd = deviceRepository.findById(d.getId()).get();
		dd.copyTo(d);
		return deviceRepository.save(dd);
	}

	@Override
	public Device get(Long id) {
		return deviceRepository.findById(id).get();
	}

	@Override
	public Device delete(long d) {
		Device device = deviceRepository.findById(d).get();
		deviceRepository.delete(device);
		return device;
	}
	@Override
	public List<Device> maj() {
		
		String[][] apis = {
				{"http://tanger.geodaki.com:3000/rpc/wdevices", "TANGER", "16"}, 
				{"http://arma.geodaki.com:3000/rpc/wdevices", "ARMA", "11"},		
				{"http://geodaki.com:3000/rpc/wdevices", "MARRAKECH", "10"},	 	
				{"http://rabat.geodaki.com:3000/rpc/wdevices", "RABAT", "9"}
		};
		for (String api[] : apis) {
			System.err.println("updating " + api[1] + "  devices");
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(api[0]);
			try {
			    // Execute the request
			    CloseableHttpResponse response = httpClient.execute(request);
			    // Get the response status code
			    int statusCode = response.getStatusLine().getStatusCode();
			    System.out.println("Response status code: " + statusCode);
			    // Read the response content 									 
			    String responseBody = EntityUtils.toString(response.getEntity());
			    //System.err.println("Response body: " + responseBody); 
			    ObjectMapper objectMapper = new ObjectMapper();

		        // Deserialize the JSON into an array of SensorData objects
		        DeviceFDB[] sensorDataArray = objectMapper.readValue(responseBody, DeviceFDB[].class);

		        // Access the deserialized objects
		        for (DeviceFDB d : sensorDataArray) {
		            //System.out.println(deviceRepository.findByConcatdev(api[1]+d.getDid()));
		            Device device = deviceRepository.findByConcatdev(api[1]+d.getDid());
		            if(device != null) {
		            	device.setName(d.getName());
		            	device.setUniqueid(d.getUniqueid());
		            	device.setCapteurs(d.getCapteur());
		            	device.setFonction(d.getFonction());
		            	device.setTypeveh(d.getTypeveh());
		            	device.setVehicule(d.getVehicule());
		            	device.setVille(villeRepo.findById(Long.valueOf(api[2])).get());
		            	device.setConcatdev(api[1]+d.getDid());
		            }else {
		            	System.err.println("Adding new device ........ ");
		            	device = new Device();
		            	device.setConcatdev(api[1]+d.getDid());
		            	device.setName(d.getName());
		            	device.setUniqueid(d.getUniqueid());
		            	device.setCapteurs(d.getCapteur());
		            	device.setFonction(d.getFonction());
		            	device.setTypeveh(d.getTypeveh());
		            	device.setVehicule(d.getVehicule());
		            	device.setVille(villeRepo.findById(Long.valueOf(api[2])).get());
		            	
		            }
		            deviceRepository.saveAndFlush(device);
		        }
			    /*JSONArray datas = new JSONArray(responseBody);
			    System.err.println(datas);	*/
			    response.close(); 			
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    try { 
			        httpClient.close();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
		}
		
		
		return getAll();
	}

	 
	 

}
