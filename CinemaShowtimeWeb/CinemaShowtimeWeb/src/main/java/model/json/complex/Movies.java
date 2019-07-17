package model.json.complex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinemaShowtime.ApiHelper;
import model.json.base.BaseComplexModel;
import model.json.movie.Movie;

public class Movies extends BaseComplexModel implements JsonList<Movie> {

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

	public void updateMoviesDetails() {
		for (Movie movie : list) {
			Movie movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
			ApiHelper.mergeMovieDetails(movie,movieDescripstion,null);
		}
	}


}
