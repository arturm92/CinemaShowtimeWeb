package util;

import model.json.complex.Cities;

public class Application {

	private static Application instance = null;
	private Cities cities;

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

}