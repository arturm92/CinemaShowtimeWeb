package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseComplexModel;
import model.json.cinema.Cinema;

public class Cinemas extends BaseComplexModel implements JsonList<Cinema> {

	@JsonProperty("cinemas")
	private List<Cinema> list;

	@Override
	public List<Cinema> getList() {
		return list;
	}

	@Override
	public void setList(List<Cinema> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (Cinema cinema : list) {
			System.out.println("CinemaId: " + cinema.getId() + " name: " + cinema.getName());
		}
	}

}
