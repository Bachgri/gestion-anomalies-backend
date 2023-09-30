package insight.api.anomalies.entity;

import java.util.List;

import lombok.Data;

@Data
public class Request {

	private String username;
	private String password;
	private List<ClientRole> roles;

        //getters and setters
}