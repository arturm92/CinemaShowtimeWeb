package util;

import model.json.complex.Cities;

public class Utils {

	private static Utils instance = null;
	private Cities cities;

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

}