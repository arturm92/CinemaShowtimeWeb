package cinemaShowtime.beans;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.MovieHelper;
import model.json.movie.Movie;


public class MovieDetailBean {

	private static MovieDetailBean instance = null;
	private Movie movie;
	
	public static MovieDetailBean getInstance() {
		if (instance == null) {
			instance = new MovieDetailBean();
		}
		return instance;
	}
	
	public void initMovieDetailBean(String movieId) {
		movie = ApiHelper.getMovieMultimedia(Long.valueOf(movieId));
		Movie movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
		MovieHelper.mergeMovieDetails(movie,movieDescripstion);
	}

	public Movie getMovie() {
		return movie;
	}

}
