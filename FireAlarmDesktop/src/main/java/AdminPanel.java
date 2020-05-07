import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminPanel {
	public static void displayAdminPanel(String name) {
	
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Admin Panel");
		window.setWidth(1200);
		window.setHeight(600);
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		Label lblTopic = new Label("Admin Panel");
		lblTopic.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		grid.add(lblTopic, 3, 0);


		Label sonsorid = new Label("Sensor-ID=");
		grid.add(sonsorid, 0, 1);
		TextField sonsoridText = new TextField();
		grid.add(sonsoridText, 1, 1);

		
		
		Label floorNo = new Label("Floor-No=");
		grid.add(floorNo, 0, 2);
		TextField floorNoText = new TextField();
		grid.add(floorNoText, 1, 2);
		
		
		
		Label roomNo = new Label("Room-No=");
		grid.add(roomNo, 0, 3);
		TextField roomNoText = new TextField();
		grid.add(roomNoText, 1, 3);
		
	
		Label IsActive = new Label("Active/Inactive=");
		grid.add(IsActive, 0, 4);
		String IsActive[] ={ "Active", "Inactive" };
		ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(IsActive));
		combo_box.getSelectionModel().select(0);
		grid.add(combo_box, 1, 4);



		Button btnEdit = new Button("Edit SensorID");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().add(btnEdit);
		grid.add(hbBtn, 0, 5);
		
		
		Label sensorIDEdit = new Label("Sensor ID=");
		grid.add(sensorIDEdit, 4, 1);
		TextField sensorIDREditText = new TextField();
		grid.add(sensorIDREditText, 5, 1);
		
		
		
		Button btnDetailEdit = new Button("Get Details For Edit");
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.CENTER);
		grid.add(btnDetailEdit, 4, 2);

		Button btnDelete = new Button("Delete");
		HBox hbBtn3 = new HBox(10);
		hbBtn3.setAlignment(Pos.CENTER);
		grid.add(btnDelete, 5, 2);






		btnEdit.setOnAction(e ->{


			if(sonsoridText.getText().equals("")  || floorNoText.getText().equals("") || roomNoText.getText().equals("")){
				Alert a = new Alert(Alert.AlertType.NONE);
				a.setAlertType(Alert.AlertType.ERROR);
				a.setContentText("Please fill all fields!!!");
				a.show();
			}else if ( ! AdminPanel.isNumeric(floorNoText.getText()) ){
				Alert a = new Alert(Alert.AlertType.NONE);
				a.setAlertType(Alert.AlertType.ERROR);
				a.setContentText("Please use numeric value for Floor number!!!");
				a.show();
			}else{
				if(btnEdit.getText().equals("Edit SensorID")){
					System.setProperty("java.security.policy", "file:allowall.policy");

					AlarmService service = null;
					try {

						String sensorId = sonsoridText.getText();
						int floorNo= Integer.parseInt(floorNoText.getText());
						String roomNo = roomNoText.getText();
						boolean status;
						if(combo_box.getSelectionModel().getSelectedIndex() == 0) {
							status= true;
						}else {
							status = false;
						}

						service = (AlarmService) Naming.lookup("//localhost/FireAlarmService");
						String result = service.addSensor(sensorId,floorNo, roomNo, status );

						try {
							JSONObject jsonObject = new JSONObject(result);
							if(!jsonObject.isEmpty()) {
								Alert a1 = new Alert(AlertType.NONE);
								a1.setAlertType(AlertType.CONFIRMATION);
								a1.setContentText("Added Successfully");
								a1.show();
							}else {
								Alert a2 = new Alert(AlertType.NONE);
								a2.setAlertType(AlertType.ERROR);
								a2.setContentText("Error occurred while adding!!!");
								a2.show();
							}

						}catch (JSONException err){
							System.out.println(err);
						}

					} catch (NotBoundException ex) {
						ex.printStackTrace();
					} catch (MalformedURLException ex) {
						System.err.println(ex.getMessage());
					} catch (RemoteException ex) {
						System.err.println(ex.getMessage());
					}
				}else if(btnEdit.getText().equals("Update Sensor")){
					System.setProperty("java.security.policy", "file:allowall.policy");

					AlarmService service = null;
					try {

						String sensorId = sonsoridText.getText();
						int floorNo= Integer.parseInt(floorNoText.getText());
						String roomNo = roomNoText.getText();
						boolean status;
						if(combo_box.getSelectionModel().getSelectedIndex() == 0) {
							status= true;
						}else {
							status = false;
						}

						service = (AlarmService) Naming.lookup("//localhost/FireAlarmService");
						String result = service.addSensor(sensorId,floorNo, roomNo, status );

						try {
							JSONObject jsonObject = new JSONObject(result);
							if(!jsonObject.isEmpty()) {
								Alert a1 = new Alert(AlertType.NONE);
								a1.setAlertType(AlertType.CONFIRMATION);
								a1.setContentText("Update Success");
								a1.show();
							}else {
								Alert a2 = new Alert(AlertType.NONE);
								a2.setAlertType(AlertType.ERROR);
								a2.setContentText("Error For updating!!!");
								a2.show();
							}

						}catch (JSONException err){
							System.out.println(err);
						}

					} catch (NotBoundException ex) {
						ex.printStackTrace();
					} catch (MalformedURLException ex) {
						System.err.println(ex.getMessage());
					} catch (RemoteException ex) {
						System.err.println(ex.getMessage());
					}


				}

			}

		});
		btnDetailEdit.setOnAction(e ->{
			System.out.println("Update Click");

			if(sensorIDREditText.getText().equals("")){
				Alert a = new Alert(Alert.AlertType.NONE);
				a.setAlertType(Alert.AlertType.ERROR);
				a.setContentText("Please use sensor ID for edit");
				a.show();
			}else{

				System.setProperty("java.security.policy", "file:allowall.policy");

				AlarmService service = null;
				try {

					String sensorId = sensorIDREditText.getText();


					service = (AlarmService) Naming.lookup("//localhost/FireAlarmService");
					String result = service.getSensorDetails(sensorIDREditText.getText());

					try {
						JSONObject jsonObject = new JSONObject(result);

						try{
							JsonObject jsonObj = new Gson().fromJson(result, JsonObject.class);
							sonsoridText.setText( jsonObj.get("sensorid").getAsString() );
							floorNoText.setText( jsonObj.get("floorNo").getAsString() );
							roomNoText.setText( jsonObj.get("roomNo").getAsString()  );

							if(jsonObj.get("active").getAsBoolean() == false){
								combo_box.getSelectionModel().select(1);
							}else{
								combo_box.getSelectionModel().select(0);
							}
							btnEdit.setText("Update Sensor");
							sensorIDREditText.setText("");
							sonsoridText.setEditable(false);
						}catch (Exception ex){
							Alert a1 = new Alert(AlertType.NONE);
							a1.setAlertType(AlertType.ERROR);
							a1.setContentText("Sensor Not Found");
							a1.show();
						}

					}catch (JSONException err){
						System.out.println(err);
					}

				} catch (NotBoundException ex) {
					ex.printStackTrace();
				} catch (MalformedURLException ex) {
					System.err.println(ex.getMessage());
				} catch (RemoteException ex) {
					System.err.println(ex.getMessage());
				}

			}


		});
		btnDelete.setOnAction(e ->{
			System.out.println("Delete Click");
			
			if(sensorIDREditText.getText().equals("")) {
				Alert a = new Alert(Alert.AlertType.NONE);
				a.setAlertType(Alert.AlertType.ERROR);
				a.setContentText("Please add sensor ID for delete");
				a.show();
			}else {

					System.setProperty("java.security.policy", "file:allowall.policy");

					AlarmService service = null;
					try {

						String sensorId = sensorIDREditText.getText();

						service = (AlarmService) Naming.lookup("//localhost/FireAlarmService");
						String result = service.deleteSensor(sensorId);

						try {
							 JSONObject jsonObject = new JSONObject(result);
							 if(!jsonObject.isEmpty()) {
									Alert a1 = new Alert(AlertType.NONE);
									a1.setAlertType(AlertType.CONFIRMATION);
									a1.setContentText("Deleted Success");
									a1.show();
							 }else {
									Alert a2 = new Alert(AlertType.NONE);
									a2.setAlertType(AlertType.ERROR);
									a2.setContentText("Error occurred");
									a2.show();
							 }

						}catch (JSONException err){
							 System.out.println(err);
						}

					} catch (NotBoundException ex) {
						ex.printStackTrace();
					} catch (MalformedURLException ex) {
						System.err.println(ex.getMessage());
					} catch (RemoteException ex) {
						System.err.println(ex.getMessage());
					}
			}

		});
		
		
		Scene scene = new Scene(grid, 300, 275);
		window.setScene(scene);

		window.showAndWait();
		
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
