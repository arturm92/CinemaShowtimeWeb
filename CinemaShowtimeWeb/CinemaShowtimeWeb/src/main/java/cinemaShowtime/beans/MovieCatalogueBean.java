package cinemaShowtime.beans;

import java.util.Date;
import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.movie.Movie;

public class MovieCatalogueBean {

	private List<Movie> movieList;
	private List<String> genres;
	private Date releaseDate;

	public Date getReleaseDate() {
		return releaseDate;
	}

	public MovieCatalogueBean() {
		movieList = ApiHelper.getNewestMovies().getMoviesWithPoster();
	}

	public List<Movie> getMovieList() {
		return movieList;
	}

	public List<String> getGenres() {
		return genres;
	}

}
