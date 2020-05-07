import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ClientApplication extends Application  {
	
	Stage window;
	
	  private TableView<SensorData> table = new TableView<SensorData>();
	    private ObservableList<SensorData> data =
	        FXCollections.observableArrayList();
	
	
	public static void main(String[] args) {
//launch
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("FireSensor Desktop Client");		
		getsensorData();
		BorderPane borderpane = new BorderPane();
		borderpane.setTop(addHBox());
		borderpane.setCenter(addVBox());
		
		Scene scene = new Scene(borderpane, 800, 400);
		window.setScene(scene);
		window.show();
		
	}
	
	public HBox addHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");

	    Button btnLogin = new Button("Login For Admin");
	    btnLogin.setPrefSize(150, 20);

	    btnLogin.setOnAction(e->{
	    	Login.adminLoginView();
		});

	    hbox.getChildren().addAll(btnLogin);

	    return hbox;
	}
	
	public VBox addVBox() {

	    final Label lbl = new Label("Fire-Sensor Monitor");
        lbl.setFont(new Font("Arial", 20));		
	    
//	     TableView table = new TableView();

	     table.setEditable(false);
	     
	     TableColumn clmSensor = new TableColumn("SensorID");
     		clmSensor.setMinWidth(100);
     		clmSensor.setCellValueFactory( new PropertyValueFactory<SensorData, String>("sensorid"));
	 
	        TableColumn colRoom = new TableColumn("RoomNumber");
	        colRoom.setMinWidth(100);
	        colRoom.setCellValueFactory( new PropertyValueFactory<SensorData, String>("room_no"));
	    
	        TableColumn colFloor = new TableColumn("FloorNumber");
	        colFloor.setMinWidth(100);
	        colFloor.setCellValueFactory( new PropertyValueFactory<SensorData, String>("floor_no"));
	        
	        TableColumn colActive = new TableColumn("Sensor Status");
	        colActive.setMinWidth(100);
	        colActive.setCellValueFactory( new PropertyValueFactory<SensorData, String>("is_active"));
	      
	        TableColumn colCo2 = new TableColumn("CO2 Level");
	        colCo2.setMinWidth(100);
	        colCo2.setCellValueFactory( new PropertyValueFactory<SensorData, String>("co2level"));
	        
	        TableColumn colSmoke = new TableColumn("Smoke Level");
	        colSmoke.setMinWidth(100);
	        colSmoke.setCellValueFactory(new PropertyValueFactory<SensorData, String>("smoke_level"));
	       
	        TableColumn colDate = new TableColumn("Date");
	        colDate.setMinWidth(100);
	        colDate.setCellValueFactory( new PropertyValueFactory<SensorData, String>("date"));
	       
	        TableColumn colTime = new TableColumn("Time");
	        colTime.setMinWidth(100);
	        colTime.setCellValueFactory( new PropertyValueFactory<SensorData, String>("time"));
	 
	        table.setItems(data);
	        
	        table.getColumns().addAll(clmSensor, colRoom, colFloor,colActive, colCo2, colSmoke, colDate, colTime);
	 
	        final VBox vbox1 = new VBox();
	        vbox1.setSpacing(5);
	        vbox1.setPadding(new Insets(10, 0, 0, 10));
	        vbox1.getChildren().addAll(lbl, table);
	     
	     
	    return vbox1;
	}
	
	public void getsensorData() {
		
		String result = "";
        HttpGet request = new HttpGet("http://localhost:8080/rest/sensordata/all");


		
		 try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(request)) {

	            // Get HttpResponse Status
	            System.out.println(response.getProtocolVersion());
	            System.out.println(response.getStatusLine().getStatusCode());
	            System.out.println(response.getStatusLine().getReasonPhrase());
	            System.out.println(response.getStatusLine().toString());

	            HttpEntity entity = response.getEntity();
	            if (entity != null) {

	                result = EntityUtils.toString(entity);

	            }

	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		data.removeAll(data);

		JsonArray arr = new Gson().fromJson(result, JsonArray.class);
		for(JsonElement e : arr){
			System.out.println(e.getAsJsonObject());
			JsonObject o = e.getAsJsonObject();

			data.add(new SensorData(
					o.get("sensor").getAsJsonObject().get("sensorid").toString(),
					o.get("sensor").getAsJsonObject().get("roomNo").toString(),
					o.get("sensor").getAsJsonObject().get("floorNo").toString(),
					o.get("sensor").getAsJsonObject().get("active").toString(),
					o.get("co2Level").toString(),
					o.get("smokeLevel").toString(),
					o.get("date").toString(),
					o.get("time").toString()));
		}


		
	}


}
	