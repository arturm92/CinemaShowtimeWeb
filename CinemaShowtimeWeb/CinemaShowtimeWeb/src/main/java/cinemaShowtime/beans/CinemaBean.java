package cinemaShowtime.beans;

import java.util.List;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.City;
import model.json.complex.Cinemas;
import util.Utils;

public class CinemaBean {

	private Cinemas cinemas;
	private City city;
	public List<Cinema> list;
	private Cinema selectedCinema;
	private MovieBean movieBean;

	public CinemaBean(City city) {
		initCinemas(city);
		System.out.println("CinemaBean started!");
	}

	public void initCinemas(City city) {
		Utils.getInstance().setCinemaSelectionVisible(true);
		this.city = city;
		this.cinemas = ApiHelper.getAllCinemasInCity(city);
		this.list = cinemas.getList();

	}

	public void select(SelectEvent selectEvent) {
		Utils.getInstance().setCinemaSelectionVisible(false);
		initMovieBean();
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

	public void initMovieBean() {
		Utils.getInstance().setMovieSelectionVisible(true);
		movieBean = new MovieBean(selectedCinema);
	}

	public MovieBean getMovieBean() {
		return movieBean;
	}

	public void setMovieBean(MovieBean movieBean) {
		this.movieBean = movieBean;
	}

	public boolean isCinemaSelectionVisible() {
		return Utils.getInstance().isCinemaSelectionVisible();
	}

}
