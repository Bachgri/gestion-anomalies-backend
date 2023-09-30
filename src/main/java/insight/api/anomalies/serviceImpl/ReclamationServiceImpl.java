package insight.api.anomalies.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import insight.api.anomalies.entity.Reclamation;
import insight.api.anomalies.repository.ReclamationRepository;
import insight.api.anomalies.service.ReclamationService;
import insight.api.anomalies.utils.Utils;

@Service
public class ReclamationServiceImpl implements ReclamationService {

	@Value("${ftp.server.adr}")
    String FTP_ADDRESS;
	@Value("${ftp.server.user}")
    String USER;
	@Value("${ftp.server.password}")
    String PASSWORD ;
	
	
	@Autowired 
	ReclamationRepository recrep;
	
	@Override
	public List<Reclamation> getAll() {
		return recrep.findAll();
	}

	@Override
	public Reclamation post(Reclamation p) { 
		return recrep.saveAndFlush(p);
	}

	@Override
	public Reclamation put(Reclamation p) { 
		return recrep.saveAndFlush(p);
	}

	@Override
	public Reclamation get(Long id) { 
		return recrep.findById(id).get();
	}

	@Override
	public Reclamation delete(Long p) {
		Reclamation pp =recrep.findById(p).get();
		recrep.delete(pp);
		return pp;
	}
	@Override
	public void setRecImg(long userid, String imageUrl) {
		
	}

	@Override
	public String upload(MultipartFile file, String recid) {
		System.err.println("reclamation : "+recid); 
						

	    FTPClient con = null;
	    try {
	    	System.err.println("----------------------------------------------------");
	        con = new FTPClient();
	        con.connect(FTP_ADDRESS, 21);// 
	        System.out.println("reply code : " + con.getReplyString());
	        System.err.println("------------------------ server : "+ con.getDefaultPort()  +"----------------------------");
	        if (FTPReply.isPositiveCompletion(con.getReplyCode())) {
	            System.out.println("Operation success. Server reply code: " + con.getReplyCode());
	        }
	        boolean connect = con.login(USER, PASSWORD);
	        System.out.println("connected? : " + connect);
	        if (connect) {
	        	System.out.println("connected succefully");
	            con.enterLocalPassiveMode(); // important!
	            //con.setFileType();
	            //String[] t = file.getOriginalFilename().toString().split(".");
	            String t = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
	            System.err.println(t + " : length "+ file.getOriginalFilename() + " : " + t);
	            //con.changeWorkingDirectory;
	            boolean created = con.makeDirectory("./" +Utils.getFullDate() + "/");
	            con.changeWorkingDirectory("./" +Utils.getFullDate() );
	            boolean createdd = con.makeDirectory("./" +Utils.getFullDay() + "/");
	            con.changeWorkingDirectory("./" +Utils.getFullDay() );
	            System.out.println("create  directory to : ./" +Utils.getFullDate() + "/"+Utils.getFullDay() + " : ");
	            if(created)
	            	System.out.println("create  directory to : ./" +Utils.getFullDate() + "/"+Utils.getFullDay() + " : " + created);
	            else
	            	System.out.println("change directory to : ./" +Utils.getFullDate() + "/"+Utils.getFullDay() + " : " + created);
	            		
	            boolean result = con.storeFile("reclamation__"+recid + t, file.getInputStream());
	            if (result) {
	            	//HttpServletResponse response = null;
	                System.out.println("You successfully uploaded " + result + "   "+ file.getOriginalFilename() + "!");
	                con.logout();
		            con.disconnect();
		            setReclamationImg(Integer.valueOf(recid), "reclamation__"+recid + t );
	                return "You successfully uploaded";
	            } else {
	            	System.out.println("Could not upload " + file.getOriginalFilename() + "!");
	            	con.logout();
	            	con.disconnect();
	            	return "Could not upload";
	            }
	            
            	
	        }else {
	        	System.out.println("failed to connect");
	        	return "failed to connect";
	        }
	    } catch (Exception e) {
	    	System.err.println("----------------------------------------------------\n");
	    	//e.printStackTrace();
	        System.err.println(e.getCause());
	        return e.getMessage();
	    }
	}

	 

	private void setReclamationImg(long recid, String name) {
		 Reclamation rec = recrep.findById(recid).get();
		rec.setImgurl(name);
		recrep.save(rec);
	}

	@Override
	public ResponseEntity<byte[]> download(long recid) {
		String server = "141.94.26.220";
        int port = 21;
        String user = "ubuntu";
        String pass = "root15963";
        //String[] ss = get(recid).getImgurl().split(".");
        //String fn = "reclamation__" + recid +"."+ss[ss.length-1];
        String fn = get(recid).getImgurl();
        System.err.println("want download file : " + fn + "\n"+get(recid).getDate_creation());
        FTPClient ftpClient = null;
        byte[] fileData = null;

        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            boolean ftpLogin = ftpClient.login(user, pass);
            System.err.println("Connected: " + ftpLogin);
            // Use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();
            // Get details of a file or directory
            String remoteFilePath = fn;
            System.err.println(get(recid).getDate_creation() +"  Changing derectory to : /"  + Utils.getFullDate(get(recid).getDate_creation()));
            System.err.println(get(recid).getDate_creation() +"  Changing derectory to : /"  + Utils.getFullDay(get(recid).getDate_creation()));
            ftpClient.changeWorkingDirectory(Utils.getFullDate(get(recid).getDate_creation()));
            ftpClient.changeWorkingDirectory(Utils.getFullDay(get(recid).getDate_creation()));
            
            FTPFile[] files = ftpClient.listFiles();
            boolean exist = false;
            int i = 0;
            for (i = 0; i < files.length; i++) {
                if (files[i].getName().equals(fn)) {
                    exist = true;
                    break;
                }
            }
            System.out.println(files.length + " files found. File exists? " + exist);
            if (exist) {
                String remoteFileName = files[i].getName();

                // Download the file to a byte array
                InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                fileData = outputStream.toByteArray();
                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded successfully: " + remoteFileName);
                ftpClient.logout();
                ftpClient.disconnect();
            } else {
                System.out.println("The specified file may not exist!");
                ftpClient.logout();
                ftpClient.disconnect();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fn);
        headers.setContentLength(fileData.length);
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        
	}
}
