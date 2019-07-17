package cinemaShowtime.beans;

import cinemaShowtime.ApiHelper;
import model.json.movie.Movie;

public class MovieDetailBean {

	private Movie movie;
	
	public MovieDetailBean(String movieId) {
		movie = ApiHelper.getMovieMultimedia(Long.valueOf(movieId));
		Movie movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
		ApiHelper.mergeMovieDetails(movie,movieDescripstion,null);
	}

	public Movie getMovie() {
		return movie;
	}

}
