package insight.api.anomalies.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor 
public class DeviceFDB { 
	private String did ;
    private String name;
    private String uniqueid;
    private String lastupdate;
    private boolean disabled;
    private String capteur;
    private String vehicule;
    private int soc;
    private String immatriculation;
    private String typeveh;
    private String fonction;
    private String can_capt;
}
