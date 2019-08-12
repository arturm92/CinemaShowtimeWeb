package model.json.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseComplexModel;
import model.json.movie.Movie;

public class Movies extends BaseComplexModel implements JsonList<Movie> {

	@JsonProperty("movies")
	private List<Movie> list;
	private HashMap<Long, Movie> movieMap;

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

	public Movie findMovie(Long movieId) {
		for (Movie movie : list) {
			if (movie.getId().compareTo(movieId) == 0) {
				return movie;
			}
		}
		System.out.println("Movie not found!");
		return null;
	}

	public List<Movie> getMoviesWithPosterList() {
		List<Movie> movieWithPosterList = new ArrayList<Movie>();
		for (Movie movie : list) {
			if(movie.getPosterImage() != null && !movie.getPosterImage().equals("/resources/img/default_poster.jpg") ) {
				movieWithPosterList.add(movie);
			}
		}
		return movieWithPosterList;
	}
	
	public HashMap<Long, Movie> getMovieMap() {
		return movieMap;
	}

	public void fillMovieMap() {
		movieMap = new HashMap<>();
		for (Movie movie : list) {
			movieMap.put(movie.getId(), movie);
		}
	}
	
}
