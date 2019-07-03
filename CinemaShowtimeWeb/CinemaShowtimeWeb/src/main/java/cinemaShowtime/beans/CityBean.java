package cinemaShowtime.beans;

import java.util.ArrayList;
import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.City;
import util.Consts;
import util.Utils;

public class CityBean {
	
	private City selectedCity;
	private CinemaBean cinemaBean;
	
	public CityBean() {
		Utils.getInstance().setCurrentEndpoint(Consts.CITIES);
		String json = ApiHelper.getDataFromApi(Consts.CITIES);
		Utils.getInstance().listValues(json);
		System.out.println("CityBean started!");
	}

	public List<City> getCitiesList(String prefix) {
		List<City> returnList = new ArrayList<City>();
		for (City city : Utils.getInstance().getCitiesList()) {
			if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
				returnList.add(city);
			}
		}
		return returnList;
	}
	
	public void getCinemaInCity() {
		cinemaBean = new CinemaBean(selectedCity);
	}

	public City getSelectedCity() {
		//City city = Utils.getInstance().getCities().findCityById(null);
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

}
