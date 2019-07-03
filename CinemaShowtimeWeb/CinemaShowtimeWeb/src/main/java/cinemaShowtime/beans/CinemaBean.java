package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.City;
import util.Consts;
import util.Utils;

public class CinemaBean {

	private City city;
	public List<Cinema> list;
	private Cinema selectedCinema;
	private MovieBean movieBean;

	public CinemaBean(City city) {
		this.city = city;
		Utils.getInstance().setCurrentEndpoint(Consts.CINEMAS);
		initCinemaList();
		System.out.println("CinemaBean started!");
	}

	private void initCinemaList() {
		String json = ApiHelper.getAllCinemasInCity(city);
		Utils.getInstance().listValues(json);
		setList(Utils.getInstance().getCinemasList());
	}

	public List<Cinema> getList() {
		return list;
	}

	public void setList(List<Cinema> list) {
		this.list = list;
	}

	public Cinema getSelectedCinema() {
		return selectedCinema;
	}

	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

	public void getCinemaShowtime() {
		movieBean = new MovieBean(selectedCinema);
		System.out.println("It's showtime");
	}

	public MovieBean getMovieBean() {
		return movieBean;
	}

	public void setMovieBean(MovieBean movieBean) {
		this.movieBean = movieBean;
	}

}
