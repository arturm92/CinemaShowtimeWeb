package util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.json.Cinema;
import model.json.City;
import model.json.Movie;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Movies;

public class Utils {

	public String currentEndpoint;

	private Cinemas cinemas;
	private Cities cities;
	private Movies movies;

	public String getUrl(String endpoint) {
		switch (endpoint) {
		case "SHOWTIMES":
			this.currentEndpoint = Consts.SHOWTIMES;
			break;
		case "CINEMAS":
			this.currentEndpoint = Consts.CINEMAS;
			break;
		case "MOVIES":
			this.currentEndpoint = Consts.MOVIES;
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
		try {
			ObjectMapper mapper = new ObjectMapper();
			switch (this.currentEndpoint) {
			case Consts.CINEMAS:
				TypeReference<Cinemas> map1 = new TypeReference<Cinemas>() {
				};
				cinemas = mapper.readValue(json, map1);
				// cinemas.showAllElements();
				break;
			case Consts.CITIES:
				TypeReference<Cities> map2 = new TypeReference<Cities>() {
				};
				cities = mapper.readValue(json, map2);
				// cities.showAllElements();
				break;
			case Consts.MOVIES:
				TypeReference<Movies> map3 = new TypeReference<Movies>() {
				};
				movies = mapper.readValue(json, map3);
				// movies.showAllElements();
				break;
			default:
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Cinema> getCinemasList() {
		return cinemas.getList();
	}

	public List<City> getCitiesList() {
		return cities.getList();
	}

	public List<Movie> getMoviesList() {
		return movies.getList();
	}

	public Object[] getForwardParams() {
		Object[] ret = new Object[2];
		switch (this.currentEndpoint) {
		case Consts.CINEMAS:
			ret[0] = getCinemasList();
			ret[1] = "/result.jsp";
			break;
		case Consts.CITIES:
			ret[0] = getCitiesList();
			ret[1] = "/result.jsp";
			break;
		case Consts.MOVIES:
			ret[0] = getMoviesList();
			ret[1] = "/result.jsp";
			break;
		default:
		}
		return ret;
	}
}
