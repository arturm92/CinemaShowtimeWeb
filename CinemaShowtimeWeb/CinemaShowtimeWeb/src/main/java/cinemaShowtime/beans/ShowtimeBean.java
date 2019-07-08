package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.Showtime;
import model.json.complex.Showtimes;
import model.json.movie.Movie;

public class ShowtimeBean {

	private Movie movie;
	private Cinema cinema;
	private Showtimes showtimes;
	private List<Showtime> list;

	public ShowtimeBean(Movie movie, Cinema cinema) {
		this.movie = movie;
		this.cinema = cinema;
		initShowtimes();
	}

	public void initShowtimes() {
		this.showtimes = ApiHelper.getMovieShowtimesInCinema(movie, cinema);
		this.list = showtimes.getList();
	}

	public List<Showtime> getList() {
		return list;
	}
}
