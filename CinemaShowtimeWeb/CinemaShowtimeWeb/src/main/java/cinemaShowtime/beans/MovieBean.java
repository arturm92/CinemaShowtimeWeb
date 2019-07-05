package cinemaShowtime.beans;

import java.util.List;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.Movie;
import model.json.complex.Movies;

public class MovieBean {
	
	private Movies movies;
	private Cinema cinema;
	public List<Movie> list;
	private Movie selectedMovie;
	private ShowtimeBean showtimeBean;

	public MovieBean(Cinema cinema) {
		initMovies(cinema);
		System.out.println("Selected cinema -" + cinema.getId());
		System.out.println("MovieBean started!");
	}
	
	public void initMovies(Cinema cinema) {
		this.cinema = cinema;
		this.movies = ApiHelper.getAllMoviesInCinema(cinema);
		this.list = movies.getList();

	}

	public void select(SelectEvent selectEvent) {
		initShowtimeBean();
	}
	
	public void initShowtimeBean() {
		System.out.println("Selected movie - " + selectedMovie.getId());
		showtimeBean = new ShowtimeBean(selectedMovie, cinema);
	}
	
	public List<Movie> getList() {
		return list;
	}

	public void setList(List<Movie> list) {
		this.list = list;
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
}
