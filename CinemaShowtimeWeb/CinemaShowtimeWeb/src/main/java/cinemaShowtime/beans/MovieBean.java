package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.Movie;
import model.json.complex.Movies;

public class MovieBean {
	
	private Movies movies;
	private Cinema cinema;
	public List<Movie> list;
	private Movie selectedMovie;

	public MovieBean(Cinema cinema) {
		initMovies(cinema);
		System.out.println("MovieBean started!");
	}
	
	public void initMovies(Cinema cinema) {
		this.cinema = cinema;
		this.movies = ApiHelper.getAllMoviesInCinema(cinema);
		this.list = movies.getList();

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
}
