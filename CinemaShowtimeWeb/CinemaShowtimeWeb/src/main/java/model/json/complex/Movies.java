package model.json.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseComplexModel;
import model.json.movie.Genre;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

public class Movies extends BaseComplexModel implements JsonList<MovieFormatted> {

	@JsonProperty("movies")
	private List<MovieFormatted> list;
	private HashMap<Long, MovieFormatted> movieMap;

	@Override
	public List<MovieFormatted> getList() {
		return list;
	}

	@Override
	public void setList(List<MovieFormatted> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (Movie movie : list) {
			System.out.println(movie.getTitle() + "/" + movie.getId() + "/" + movie.getPosterImage());
			for (Genre genre : movie.getGenre()) {
				System.out.println(genre.getName() + "/" + genre.getId());
			}
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

	public List<MovieFormatted> getMoviesWithPosterList() {
		List<MovieFormatted> movieWithPosterList = new ArrayList<MovieFormatted>();
		for (MovieFormatted movie : list) {
			if (movie.getPosterImage() != null && !movie.getPosterImage().equals("/resources/img/default_poster.jpg")) {
				movieWithPosterList.add(movie);
			}
		}
		return movieWithPosterList;
	}

	public HashMap<Long, MovieFormatted> getMovieMap() {
		return movieMap;
	}

	public void fillMovieMap() {
		movieMap = new HashMap<>();
		// System.out.println("******MOVIE_MAP*******");
		for (MovieFormatted movie : list) {
			movieMap.put(movie.getId(), movie);
			// System.out.println(movie.getTitle() + "/" + movie.getId() + "/" +
			// movie.getPosterImage());
		}
	}

}
