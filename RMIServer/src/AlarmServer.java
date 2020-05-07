

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AlarmServer extends UnicastRemoteObject implements AlarmService{
	
	  protected AlarmServer() throws RemoteException {
	        super();
	    }

	    @Override
	    public String addFireAlarmSensor(String sensorId, int floorNumber, String roomNumber, boolean active) throws RemoteException {
	      
	    	String result = "";
			HttpPost post = new HttpPost("http://localhost:8080/rest/sensor/load");

			post.addHeader("content-type", "application/json");		

			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"sensorId\":"+sensorId+",");
			json.append("\"floorNumber\":\""+floorNumber+"\"");
			json.append("\"roomNumber\":\""+roomNumber+"\"");
			json.append("\"active\":\""+active+"\"");
			json.append("}");

			System.out.println(json);

			// send a JSON data
			try {
				post.setEntity(new StringEntity(json.toString()));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(post)) {

				result = EntityUtils.toString(response.getEntity());
			}catch (Exception e) {
				
			}

			System.out.println(result);
			
			return result;
	    	

	    }

	    @Override
	    public String updateFireAlarmSensor(String sensorId, int floorNumber, String roomNumber, boolean active) throws RemoteException {
	      
	    	String result = "";
			HttpPost post = new HttpPost("http://localhost:8080/rest/sensor/load");

			post.addHeader("content-type", "application/json");		

			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"sensorId\":"+sensorId+",");
			json.append("\"floorNumber\":\""+floorNumber+"\"");
			json.append("\"roomNumber\":\""+roomNumber+"\"");
			json.append("\"active\":\""+active+"\"");
			json.append("}");

			System.out.println(json);

			// send a JSON data
			try {
				post.setEntity(new StringEntity(json.toString()));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(post)) {

				result = EntityUtils.toString(response.getEntity());
			}catch (Exception e) {
				
			}

			System.out.println(result);
			
			return result;
	    	
	    }

	    @Override
	    public String deleteFireAlarmSensor(String sensorId) throws RemoteException {
	    	String result = "";
			HttpPost post = new HttpPost("http://localhost:8080/rest/sensor/delete/"+sensorId+"");


			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(post)) {

				result = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(result);
			
			return result;
	    	
	    	
	     
	    }

	    @Override
	    public String getFireAlarmSensorDetails(String sensorId) throws RemoteException {



			String result = "";
			HttpGet get = new HttpGet("http://localhost:8080/rest/sensor/getsensor/"+sensorId+"");


			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(get)) {

				result = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(result);
			
			return result;
	
	    }

	    public  String Login(String uname, String pwd) throws RemoteException {
	    	String res="";
	    	try {
				res =sendPOSTAdminLogin(uname,pwd);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	        return res;
	    }

	    public static void main(String[] args) {
	        System.setProperty("java.security.policy", "file:allowall.policy");

	        try{
	            AlarmServer svr = new AlarmServer();
	            // Bind the remote object's stub in the registry
	            Registry registry = LocateRegistry.getRegistry();
	            registry.bind("AlarmService", svr);

	            System.out.println ("Service started....");
	        }
	        catch(RemoteException re){
	            System.err.println(re.getMessage());
	        }
	        catch(AlreadyBoundException abe){
	            System.err.println(abe.getMessage());
	        }
	    }
	    
	    
	    
	    private static String sendPOSTAdminLogin(String uname, String pwd) throws IOException {

			String result = "";
			HttpPost post = new HttpPost("http://localhost:8080/rest/users/auth");

			post.addHeader("content-type", "application/json");		

			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"uname\":"+uname+",");
			json.append("\"pwd\":\""+pwd+"\"");
			json.append("}");

			System.out.println(json);

			// send a JSON data
			post.setEntity(new StringEntity(json.toString()));

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(post)) {

				result = EntityUtils.toString(response.getEntity());
			}

			System.out.println(result);
			
			return result;
			
		}

		@Override
		public String getSensorDetails() throws RemoteException {
		
			String result = "";
			HttpPost post = new HttpPost("http://localhost:8080/rest/sensor/all");

			post.addHeader("content-type", "application/json");		

			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("}");

			System.out.println(json);

			// send a JSON data
			try {
				post.setEntity(new StringEntity(json.toString()));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
				 CloseableHttpResponse response = httpClient.execute(post)) {

				result = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(result);
			
			return result;
	    	
			
			

		}
	

}
