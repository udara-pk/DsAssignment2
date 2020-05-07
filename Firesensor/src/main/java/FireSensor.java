import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import utils.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class FireSensor {
	public static void main(String[] args) {

		try {
			while (true) {

				String result = sendPOST(Utils.SENDPOSTSENSORDATAURL);

				System.out.println(result);

				try {
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String sendPOST(String url) throws IOException {

		String results = "";
		HttpPost post = new HttpPost(url);

		post.addHeader("content-type", "application/json");

		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();


		String sensorID = "sensor1";
		int floorNo = 1;
		String roomNo = "10";
		boolean isActive = true;
		int smokeLevel = ThreadLocalRandom.current().nextInt(0, 10 + 1);
		int co2Level = ThreadLocalRandom.current().nextInt(0, 10 + 1);




		String dt = date.format(now);
		String tm = time.format(now);
		String sensor = "{\"sensorid\":\"" + sensorID + "\"}";
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"sensordataid\":\"" + sensorID + ":" + dt + ":" + tm + "\",");
		json.append("\"sensor\":" + sensor + ",");
		json.append("\"floorNo\":\"" + floorNo + "\",");
		json.append("\"roomNo\":\"" + roomNo + "\",");
		json.append("\"isActive\":\"" + isActive + "\",");
		json.append("\"smokeLevel\":\"" + smokeLevel + "\",");
		json.append("\"co2Level\":\"" + co2Level + "\",");
		json.append("\"date\":\"" + dt + "\",");
		json.append("\"time\":\"" + tm + "\"");
		json.append("}");

		System.out.println(json);

		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			results = EntityUtils.toString(response.getEntity());
		}

		return results;
	}

}
