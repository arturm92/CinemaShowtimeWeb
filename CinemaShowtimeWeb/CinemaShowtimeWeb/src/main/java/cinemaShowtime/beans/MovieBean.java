package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.Movie;
import util.Consts;
import util.Utils;

public class MovieBean {

	private Cinema cinema;
	public List<Movie> list;
	
	public MovieBean(Cinema cinema) {
		this.cinema = cinema;
		Utils.getInstance().setCurrentEndpoint(Consts.MOVIES);
		initMoviesList();
		System.out.println("CinemaBean started!");
	}

	private void initMoviesList() {
		String json = ApiHelper.getAllMoviesInCinema(cinema);
		Utils.getInstance().listValues(json);
		setList(Utils.getInstance().getMoviesList());
	}

	public List<Movie> getList() {
		return list;
	}

	public void setList(List<Movie> list) {
		this.list = list;
	}
}
