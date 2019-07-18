package cinemaShowtime.beans;

import cinemaShowtime.ApiHelper;
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
		ApiHelper.mergeMovieDetails(movie,movieDescripstion,null);
	}

	public Movie getMovie() {
		return movie;
	}

}
