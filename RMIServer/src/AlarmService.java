
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AlarmService {
	
    public String addFireAlarmSensor(String sensorId, int floorNumber , String roomNumber , boolean i) throws RemoteException;
    public String updateFireAlarmSensor(String sensorId, int floorNumber , String roomNumber , boolean active) throws RemoteException;
    public String deleteFireAlarmSensor(String sensorId) throws RemoteException;
    public String getFireAlarmSensorDetails(String sensorId) throws RemoteException;
    public String Login(String uname, String pwd) throws RemoteException;
    public String getSensorDetails() throws RemoteException;
	
	
}
