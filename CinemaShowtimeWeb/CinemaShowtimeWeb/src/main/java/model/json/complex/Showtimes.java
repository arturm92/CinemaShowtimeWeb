package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.Showtime;
import model.json.base.BaseComplexModel;

public class Showtimes extends BaseComplexModel implements JsonList<Showtime> {

	@JsonProperty("showtimes")
	private List<Showtime> list;

	@Override
	public List<Showtime> getList() {
		return list;
	}

	@Override
	public void setList(List<Showtime> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (Showtime showtime : list) {
			System.out.println("ShowtimesId: " + showtime.getId() + " MovieId: " + showtime.getMovieId() + " StartAt" + showtime.getStartAt());
		}
	}

}
