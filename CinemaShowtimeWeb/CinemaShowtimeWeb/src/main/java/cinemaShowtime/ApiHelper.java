package cinemaShowtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import model.json.City;
import util.Consts;

public class ApiHelper {

	public static String getDataFromApi(String url) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("X-API-Key", Consts.API_KEY);
			HttpResponse response = client.execute(request);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			return readResultContent(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String readResultContent(HttpResponse response) throws UnsupportedEncodingException, IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	public static String getAllCinemasInCity(City city) {
		String params =  "?location=" + city.getLat() + "," + city.getLon();
		params += "&distance=30";
		return getDataFromApi(Consts.CINEMAS + params);
	}
	
	public static String getAllMoviesInCinema(String cinemaId) {
		String params =  "?cinema_id=" + cinemaId;
		return getDataFromApi(Consts.MOVIES + params);
	}
}
