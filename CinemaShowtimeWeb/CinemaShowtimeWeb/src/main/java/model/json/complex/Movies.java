package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.JsonModel;
import model.json.Movie;

public class Movies extends JsonModel implements JsonList<Movie> {

	@JsonProperty("movies")
	private List<Movie> list;

	@Override
	public List<Movie> getList() {
		return list;
	}

	@Override
	public void setList(List<Movie> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (Movie movie : list) {
			System.out.println("MovieId: " + movie.getId() + " title: " + movie.getTitle());
		}

	}

}
