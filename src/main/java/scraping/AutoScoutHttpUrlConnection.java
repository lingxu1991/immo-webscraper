package scraping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class AutoScoutHttpUrlConnection {
	private final static String urlPrefix = "https://www.autoscout24.de/angebote/";

	public String getHTMLPageSouce(String objectId) {
		URL url;
		StringBuffer content = new StringBuffer();
		try {
			url = new URL(urlPrefix + objectId);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			connection.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();
	}
}
