package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.City;
import model.json.JsonModel;

public class Cities extends JsonModel implements JsonList<City>{
	
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
			System.out.println("CityId: " + city.getId() + " name: " + city.getName());
		}
	}
	
	

}
