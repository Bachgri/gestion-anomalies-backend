package insight.api.anomalies.dto;

import java.util.List;

import insight.api.anomalies.entity.Probleme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private List<ProblemeDTO> problemes;
     
    
}