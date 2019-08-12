package cinemaShowtime.beans;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import model.json.City;
import model.json.complex.Cities;
import util.Utils;

public class CityBean {

	private Cities cities;
	private City selectedCity;
	private CinemaBean cinemaBean;
	private List<City> citiesQuickList;

	public CityBean() {
		initCities();
		createCitiesQuickSelection();
		System.out.println("CityBean started!");
	}

	public void initCities() {
		this.cities = ApiHelper.getCitiesFromApi();
		Utils.getInstance().setCities(cities);
		Utils.getInstance().setCitySelectionVisible(true);
	}

	private void createCitiesQuickSelection() {
		citiesQuickList = new ArrayList<City>();
		long startTime = System.currentTimeMillis();
		
		citiesQuickList.add(findCity("bydgoszcz"));
		citiesQuickList.add(findCity("gdańsk"));
		citiesQuickList.add(findCity("gdynia"));
		citiesQuickList.add(findCity("katowice"));
		citiesQuickList.add(findCity("krakow"));
		citiesQuickList.add(findCity("poznań"));
		citiesQuickList.add(findCity("rzeszow"));
		citiesQuickList.add(findCity("szczecin"));
		citiesQuickList.add(findCity("torun"));
		citiesQuickList.add(findCity("warszawa"));
		citiesQuickList.add(findCity("wrocław"));
		citiesQuickList.add(findCity("zielona gora"));

		long stopTime = System.currentTimeMillis();
		System.out.println("Cities find in " + ((stopTime - startTime) / 1000) + "second");
	}

	public Cities getCities() {
		if (cities == null) {
			initCities();
		}
		return cities;
	}

	public List<City> getCitiesList(String prefix) {
		List<City> returnList = new ArrayList<City>();
		if (prefix.length() > 0) {
			for (City city : getCities().getList()) {
				if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(city);
				}
			}
		} else {
			returnList.addAll(getCities().getList());
		}
		return returnList;
	}

	public City findCity(String prefix) {
		for (City city : getCities().getList()) {
			if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
				return city;
			}
		}
		return null;
	}

	public void select(SelectEvent event) {
		Utils.getInstance().setCitySelectionVisible(false);
		initCinemaBean();
	}
	
	public void quickSelect() {
		Utils.getInstance().setCitySelectionVisible(false);
		initCinemaBean();
	}

	public void initCinemaBean() {
		System.out.println("City id - " + selectedCity.getId());
		cinemaBean = new CinemaBean(selectedCity);
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

	public CinemaBean getCinemaBean() {
		return cinemaBean;
	}

	public void setCinemaBean(CinemaBean cinemaBean) {
		this.cinemaBean = cinemaBean;
	}

	public boolean isCitySelectionVisible() {
		return Utils.getInstance().isCitySelectionVisible();
	}

	public List<City> getCitiesQuickList() {
		return citiesQuickList;
	}

}