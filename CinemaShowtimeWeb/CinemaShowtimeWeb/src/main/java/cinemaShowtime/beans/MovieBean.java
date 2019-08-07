package cinemaShowtime.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.complex.Movies;
import model.json.movie.Movie;
import util.Utils;

public class MovieBean {

	private Movies movies;
	private Cinema cinema;
	private Movie selectedMovie;
	private ShowtimeBean showtimeBean;
	private MovieDetailBean movieDetailBean;

	public MovieBean(Cinema cinema) {
		initMovies(cinema);
		System.out.println("Selected cinema -" + cinema.getId());
		System.out.println("MovieBean started!");
	}

	public void initMovies(Cinema cinema) {
		this.cinema = cinema;

		long startTime = System.currentTimeMillis();
		this.movies = ApiHelper.getAllMoviesInCinema(cinema);
		long stopTime = System.currentTimeMillis();
		System.out.println("movies download " + ((stopTime - startTime) / 1000) + " second");

		Movies moviesDescription = ApiHelper.getAllMoviesDescriptionInCinema(cinema);
		mergeMovieDetails(movies, moviesDescription);
		
		long stopTime2 = System.currentTimeMillis();
		System.out.println("movies description download " + ((stopTime2 - stopTime) / 1000) + " second");

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
			ec.redirect("/CinemaShowtimeWeb/movies/movieDetail/index.xhtml");
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

	public List<Movie> getList() {
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
