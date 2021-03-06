package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinemaShowtime.utils.Logger;
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
			Logger.log("[CINEMA]" + cinema.getName() + "/" + cinema.getId());
		}
	}

	public Cinema findCinemaByName(String cinemaName) {
		if (cinemaName != null && !cinemaName.isEmpty()) {
			for (Cinema cinema : list) {
				if (cinema.getName().equals(cinemaName)) {
					return cinema;
				}
			}
		}
		return null;
	}

	public Cinema findCinemaById(Long cinemaId) {
		for (Cinema cinema : list) {
			Logger.log("CINEMA ID : " + cinema.getId() + "/" +cinema.getName());
			if (cinema.getId().equals(cinemaId)) {
				return cinema;
			}
		}
		return null;
	}

}