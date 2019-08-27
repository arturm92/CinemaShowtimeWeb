package cinemaShowtime.beans;

import java.util.List;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import model.json.City;
import model.json.cinema.Cinema;
import model.json.complex.Cinemas;
import util.Consts;
import util.Utils;

public class CinemaBean {

	private Cinemas cinemas;
	private City city;
	private Cinema selectedCinema;
	private MovieBean movieBean;

	public CinemaBean(City city) {
		initCinemas(city);
		System.out.println("CinemaBean started!");
	}

	public void initCinemas(City city) {
		Utils.getInstance().setCinemaSelectionVisible(true);
		this.city = city;
		Filter filter = prepareFilter();
		this.cinemas = ApiHelper.getCinemas(filter);

	}
	

	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addFilterParam(Filter.Parameter.LOCATION, city.getLat() + "," + city.getLon());
		filter.addFilterParam(Filter.Parameter.DISTANCE, "10");
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}


	public void select(SelectEvent selectEvent) {
		Utils.getInstance().setCinemaSelectionVisible(false);
		initMovieBean();
	}

	public List<Cinema> getList() {
		return cinemas.getList();
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
	
	public void firstStepClicked() {
		Utils.getInstance().goToFirstStep();
	}

}
