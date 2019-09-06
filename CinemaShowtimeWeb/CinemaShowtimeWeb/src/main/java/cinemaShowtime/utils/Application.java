package cinemaShowtime.utils;

import java.util.List;

import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.movie.Genre;

public class Application {

	private static Application instance = null;
	private Cities cities;
	private Cinemas cinemas;
	private List<Genre> genreList;

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}

	public Cities getCities() {
		return cities;
	}

	public Cinemas getCinemas() {
		return cinemas;
	}

	public void setCinemas(Cinemas cinemas) {
		this.cinemas = cinemas;
	}

	public List<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(List<Genre> genreList) {
		this.genreList = genreList;
	}


}