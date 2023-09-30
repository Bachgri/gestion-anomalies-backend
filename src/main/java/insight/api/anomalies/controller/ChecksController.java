package insight.api.anomalies.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import insight.api.anomalies.entity.Check;
import insight.api.anomalies.service.CheckService;
import insight.api.anomalies.utils.CheckValue;
import insight.api.anomalies.utils.Utils;

@RestController 
@RequestMapping("/Api/checks")
@CrossOrigin(originPatterns = "http://*:*")
public class ChecksController {
	
	CheckService checkService;
	
	
	public ChecksController(CheckService checkService) {
		super();
		this.checkService = checkService;
	}
	@PostMapping("/in")
	public ResponseEntity<Check> in(@RequestBody Check check){
		System.err.println("Check out .... ");
		check.setCheckValue(CheckValue.IN.toString());
		check.setDh(Utils.currentTimeStamp());
		return ResponseEntity.ok(checkService.post(check));
	}
	@PostMapping("/out") 
	public ResponseEntity<Check> out(@RequestBody Check check){
		System.err.println("Check in .... ");
		check.setCheckValue(CheckValue.OUT.toString());
		check.setDh(Utils.currentTimeStamp());
		return ResponseEntity.ok(checkService.post(check));
	}
	@GetMapping("/all")
	public ResponseEntity<List<Check>> checks(){
		System.err.println("get all checks .... ");
		return ResponseEntity.ok(checkService.getAll());
	}
	
	
	
}
