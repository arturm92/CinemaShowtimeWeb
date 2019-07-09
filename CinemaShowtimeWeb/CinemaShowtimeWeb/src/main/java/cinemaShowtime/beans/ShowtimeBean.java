package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.Showtime;
import model.json.complex.Showtimes;
import model.json.movie.Movie;
import util.Utils;

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
		Utils.getInstance().setShowtimeSelectionVisible(true);
	}

	public List<Showtime> getList() {
		return list;
	}

	public Movie getMovie() {
		return movie;
	}
	
	public boolean isShowtimeSelectionVisible() {
		return Utils.getInstance().isShowtimeSelectionVisible();
	}
}
