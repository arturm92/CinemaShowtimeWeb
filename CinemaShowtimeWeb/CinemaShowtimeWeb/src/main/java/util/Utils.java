package util;

import model.json.complex.Cities;

public class Utils {

	private static Utils instance = null;
	private Cities cities;

	private boolean citySelectionVisible;
	private boolean cinemaSelectionVisible;
	private boolean movieSelectionVisible;
	private boolean showtimeSelectionVisible;
	
	public static Utils getInstance() {
		if (instance == null) {
			instance = new Utils();
		}
		return instance;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}

	public Cities getCities() {
		return cities;
	}

	public boolean isCitySelectionVisible() {
		return citySelectionVisible;
	}

	public void setCitySelectionVisible(boolean citySelectionVisible) {
		this.citySelectionVisible = citySelectionVisible;
	}

	public boolean isCinemaSelectionVisible() {
		return cinemaSelectionVisible;
	}

	public void setCinemaSelectionVisible(boolean cinemaSelectionVisible) {
		this.cinemaSelectionVisible = cinemaSelectionVisible;
	}

	public boolean isMovieSelectionVisible() {
		return movieSelectionVisible;
	}

	public void setMovieSelectionVisible(boolean movieSelectionVisible) {
		this.movieSelectionVisible = movieSelectionVisible;
	}

	public boolean isShowtimeSelectionVisible() {
		return showtimeSelectionVisible;
	}

	public void setShowtimeSelectionVisible(boolean showtimeSelectionVisible) {
		this.showtimeSelectionVisible = showtimeSelectionVisible;
	}

}