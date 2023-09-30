package insight.api.anomalies.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import insight.api.anomalies.entity.*;

public interface ReclamationService {
	public List<Reclamation> getAll();
	public Reclamation post(Reclamation p);
	public Reclamation put(Reclamation p);
	public Reclamation get(Long id);
	public Reclamation delete(Long p);
	public void setRecImg(long userid, String imageUrl);
	public String upload(MultipartFile file, String recid);
	public ResponseEntity<byte[]> download(long recid);
}
