package cinemaShowtime.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import model.json.City;
import util.Utils;

public class CityBean {
	
	private City selectedCity;
	private City autoCompleteCity;

	public CityBean() {
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

	public City getAutoCompleteCity() {
		return autoCompleteCity;
	}

	public void setAutoCompleteCity(City autoCompleteCity) {
		this.autoCompleteCity = autoCompleteCity;
	}


	public City getSelectedCity() {
		City city = Utils.getInstance().getCities().findCityById(null);
		setSelectedCity(city);
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

}
