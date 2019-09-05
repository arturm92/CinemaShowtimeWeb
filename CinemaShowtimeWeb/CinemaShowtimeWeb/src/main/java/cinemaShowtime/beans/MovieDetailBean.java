package cinemaShowtime.beans;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

public class MovieDetailBean {

	private static MovieDetailBean instance = null;
	private MovieFormatted movie;

	public static MovieDetailBean getInstance() {
		if (instance == null) {
			instance = new MovieDetailBean();
		}
		return instance;
	}

	public void initMovieDetailBean(Long movieId) {
		movie = ApiHelper.getMovieMultimedia(movieId);
		MovieFormatted movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
		MovieHelper.mergeMovieDetails(movie, movieDescripstion);
	}
	
	public void initMovieDetailBean(String movieId) {
		movie = ApiHelper.getMovieMultimedia(Long.valueOf(movieId));
		MovieFormatted movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
		MovieHelper.mergeMovieDetails(movie, movieDescripstion);
	}

	public void goToMovieWebsite() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String link = externalContext.getRequestParameterMap().get("link");
		PrimeFaces.current().executeScript("window.open('" + link + "', '_newtab')");
	}
	
	public Movie getMovie() {
		return movie;
	}

}
