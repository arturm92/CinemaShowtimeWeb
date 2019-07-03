package util;

import java.io.IOException;
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

	private static Utils instance = null;

	public String currentEndpoint;

	private Cinemas cinemas;
	private Cities cities;
	private Movies movies;

	public static Utils getInstance() {
		if (instance == null)
			instance = new Utils();

		return instance;
	}

	public void setCurrentEndpoint(String endpoint) {
		this.currentEndpoint = endpoint;
	}

	public void listValues(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			switch (currentEndpoint) {
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

	public Object getList() {
		switch (currentEndpoint) {
		case Consts.CINEMAS:
			return getCinemasList();
		case Consts.MOVIES:
			return getMoviesList();
		case Consts.CITIES:
			return getCitiesList();
		}
		return null;
	}

	public Object[] getForwardParams() {
		Object[] ret = new Object[2];
		switch (currentEndpoint) {
		case Consts.CINEMAS:
			ret[0] = getCinemasList();
			ret[1] = "/result.jsp";
			break;
		case Consts.CITIES:
			ret[0] = getCitiesList();
			ret[1] = "/result2.xhtml";
			break;
		case Consts.MOVIES:
			ret[0] = getMoviesList();
			ret[1] = "/result2.xhtml";
			break;
		default:
		}
		return ret;
	}

	public Cinemas getCinemas() {
		return cinemas;
	}

	public Cities getCities() {
		return cities;
	}

	public Movies getMovies() {
		return movies;
	}

}
