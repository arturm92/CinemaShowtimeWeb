package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinemaShowtime.utils.Logger;
import model.json.City;
import model.json.base.BaseComplexModel;

public class Cities extends BaseComplexModel implements JsonList<City> {

	@JsonProperty("cities")
	private List<City> list;

	@Override
	public List<City> getList() {
		return list;
	}

	@Override
	public void setList(List<City> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (City city : list) {
			Logger.log("[CITY]" + city.getName() + "/" + city.getId());
		}
	}

	public City findCityById(Integer cityId) {
		for (City city : list) {
			if (city.getId().compareTo(new Long(cityId)) == 0) {
				return city;
			}
		}
		return null;
	}

	public City findCityByName(String cityName) {
		if (cityName != null && !cityName.isEmpty()) {
			for (City city : list) {
				if (city.getName().equals(cityName)) {
					return city;
				}
			}
		}
		return null;
	}

}
