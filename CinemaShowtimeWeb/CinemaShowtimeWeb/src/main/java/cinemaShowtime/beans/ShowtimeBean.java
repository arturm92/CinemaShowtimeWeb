package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import model.json.Showtime;
import model.json.ShowtimeDay;
import model.json.cinema.Cinema;
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
		Filter filter = prepareFilter();
		this.showtimes = ApiHelper.getMovieShowtimesInCinema(filter);
		this.list = showtimes.getList();
		Utils.getInstance().setShowtimeSelectionVisible(true);
		
		/*List<ShowtimeDay> showtimeDayList = showtimes.getNormalizeList();
		for (ShowtimeDay sd : showtimeDayList) {
			System.out.println(sd.getDate());
			for (String h : sd.getHours()) {
				System.out.println(h);
			}
		}*/
		
	}
	
	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addQueryParam(Filter.Query.CINEMA_ID, cinema.getId().toString());
		filter.addQueryParam(Filter.Query.MOVIE_ID, movie.getId().toString());
		return filter;
	}

	
	public List<ShowtimeDay> getShowtimeDayList(){
		return showtimes.getNormalizeList();
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

	public void firstStepClicked() {
		Utils.getInstance().goToFirstStep();
	}

	public void secondStepClicked() {
		Utils.getInstance().goToSecondStep();
	}

	public void thirdStepClicked() {
		Utils.getInstance().goToThirdStep();
	}
}
