package util;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.json.Cinema;
import model.json.City;
import model.json.complex.Cinemas;
import model.json.complex.Cities;

public class Utils {

	public String currentEndpoint;

	public String getUrl(String endpoint) {
		switch (endpoint) {
		case "SHOWTIMES":
			this.currentEndpoint = Consts.SHOWTIMES;
			break;
		case "CINEMAS":
			this.currentEndpoint = Consts.CINEMAS;
			break;
		case "MOVIES":
			this.currentEndpoint = Consts.MOVEIS;
			break;
		case "CITIES":
			this.currentEndpoint = Consts.CITIES;
			break;
		default:
			this.currentEndpoint = null;
			break;
		}
		return currentEndpoint;
	}

	public void listValues(String json) {
		switch (this.currentEndpoint) {
		case Consts.CINEMAS:
			listCinemas(json);
			break;
		case Consts.CITIES:
			listCites(json);
			break;
		default:
		}

	}

	public void listCinemas(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			TypeReference<Cinemas> mapType = new TypeReference<Cinemas>() {};
			Cinemas cinemas = mapper.readValue(json, mapType);
			for (Cinema cinema : cinemas.getList()) {
				System.out.println(cinema.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void listCites(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			TypeReference<Cities> mapType = new TypeReference<Cities>() {};
			Cities cities = mapper.readValue(json, mapType);
			for (City city : cities.getList()) {
				System.out.println(city.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
