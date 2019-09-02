package cinemaShowtime.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.cinema.Cinema;
import model.json.complex.Movies;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.Utils;

public class MovieBean {

	private Movies movies;
	private Movies moviePosters;
	private Cinema cinema;
	private Movie selectedMovie;
	private ShowtimeBean showtimeBean;
	private MovieDetailBean movieDetailBean;

	public MovieBean(Cinema cinema) {
		long startTime = System.currentTimeMillis();
		this.cinema = cinema;
		initMovies();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("MovieBean started in " + ((stopTime - startTime) / 1000) + " second");
		
	}

	public void initMovies() {
		Filter filter = prepareFilter();
		this.movies = ApiHelper.getMoviesInCinema(filter);
		//Movies moviesDescription = ApiHelper.getAllMoviesDescriptionInCinema(cinema);
		//mergeMovieDetails(movies, moviesDescription);
		
		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

	}
	
	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addQueryParam(Filter.Query.CINEMA_ID, cinema.getId().toString());
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	public void select(SelectEvent selectEvent) {
		Utils.getInstance().setMovieSelectionVisible(false);
		initShowtimeBean();
	}

	public void select() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String movieId = ec.getRequestParameterMap().get("movieId");
		selectedMovie = movies.findMovie(Long.valueOf(movieId));
		Utils.getInstance().setMovieSelectionVisible(false);
		initShowtimeBean();
	}

	public void showMovieDetail() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initShowtimeBean() {
		System.out.println("Selected movie - " + selectedMovie.getId());
		showtimeBean = new ShowtimeBean(selectedMovie, cinema);
	}

	public void mergeMovieDetails(Movies movies, Movies moviesDescription) {
		moviesDescription.fillMovieMap();
		for (Movie movie : movies.getList()) {
			Movie movieDescripstion = moviesDescription.getMovieMap().get(movie.getId());
			movie.setDescription(movieDescripstion.getDescription());
			movie.setTitle(movieDescripstion.getTitle());
			movie.setOriginalTitle(movieDescripstion.getOriginalTitle());
			movie.setGenre(movieDescripstion.getGenre());
			movie.setCast(movieDescripstion.getCast());
			movie.setCrew(movieDescripstion.getCrew());
			movie.setWebsite(movieDescripstion.getWebsite());
		}
	}

	public List<MovieFormatted> getList() {
		return movies.getList();
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public ShowtimeBean getShowtimeBean() {
		return showtimeBean;
	}

	public void setShowtimeBean(ShowtimeBean showtimeBean) {
		this.showtimeBean = showtimeBean;
	}

	public boolean isMovieSelectionVisible() {
		return Utils.getInstance().isMovieSelectionVisible();
	}

	public void firstStepClicked() {
		Utils.getInstance().goToFirstStep();
	}

	public void secondStepClicked() {
		Utils.getInstance().goToSecondStep();
	}

	public MovieDetailBean getMovieDetailBean() {
		return movieDetailBean;
	}
}
