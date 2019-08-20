package app.example.RoboticProcessAutomation;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Wrapper class for QRadar.
 * 
 * @author Rahul Reddy Ravipally
 *
 */

public class QRadar {

	String name = "";
	String password = "";
	String host_qradar = "";
	
	String content = "Trigger the validation bot";
	
	/**
	 * Constructor
	 * 
	 * @param name 
	 * @param password
	 * @param host_qradar
	 */
	
	public QRadar(String name, String password, String host_qradar) {
		this.name = name;
		this.password = password;
		this.host_qradar = host_qradar;

	}

	ArrayList<String> new_ids = new ArrayList<String>(); // All the new offence id's are added here.
	
	public void send_offences(ArrayList<String> ids)
			throws ClientProtocolException, IOException, ParseException, InterruptedException {


		String authString = name + ":" + password;
		
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		String authcode = "Basic" + " " + authStringEnc;

		final SSLConnectionSocketFactory sslsf;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault(), NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();

		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		cm.setMaxTotal(100);

		HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm).build();

		String url = "https://" + host_qradar + "/api/siem/offenses";
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Version", "7.0");
		httpGet.addHeader("Authorization", authcode);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");

		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		String responseString = EntityUtils.toString(entity, "UTF-8");

		JSONParser parser = new JSONParser();

		JSONArray jsonArr = (JSONArray) parser.parse(responseString);

		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			String id = jsonObj.get("id").toString();
			if (!new_ids.contains(id)) {
				if (jsonObj.get("status").toString().equals("OPEN")) {
					new_ids.add(id);
					// You can add your own offence source below
					System.out.print("New offence detected........");
					TimeUnit.MILLISECONDS.sleep(100);
					System.out.print("...");
					TimeUnit.MILLISECONDS.sleep(100);
					System.out.print("...");
					TimeUnit.MILLISECONDS.sleep(100);
					System.out.print("...");
					TimeUnit.MILLISECONDS.sleep(100);
					System.out.println("...");
					TimeUnit.MILLISECONDS.sleep(1000);
					System.out.println("Offence Name : "+jsonObj.get("offense_source").toString());
					TimeUnit.MILLISECONDS.sleep(1000);
					System.out.println("Running the validation bot, to check if the offence is valid");
					String path_trigger = ""; 
					Files.write(Paths.get(path_trigger), content.getBytes());					
				}
			}
		}

	}

}
