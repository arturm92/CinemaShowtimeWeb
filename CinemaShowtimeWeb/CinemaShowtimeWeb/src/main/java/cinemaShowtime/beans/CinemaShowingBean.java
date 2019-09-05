package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.City;
import model.json.Showtime;
import model.json.ShowtimeDay;
import model.json.cinema.Cinema;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.Application;

@ManagedBean(name = "cinemaShowingBean", eager = true)
@SessionScoped
public class CinemaShowingBean {

	private Cities cities;
	private Cinemas cinemas;
	private Movies movies;
	private Movies moviePosters;
	private Showtimes showtimes;

	private City selectedCity;
	private Cinema selectedCinema;
	private Movie selectedMovie;

	private List<City> citiesQuickList;

	private boolean citySelectionVisible;
	private boolean cinemaSelectionVisible;
	private boolean movieSelectionVisible;
	private boolean showtimeSelectionVisible;

	public CinemaShowingBean() {
		initCities();
		createCitiesQuickSelection();
		System.out.println("CityBean started!");
	}

	public void initCities() {
		this.cities = ApiHelper.getCitiesFromApi();
		Application.getInstance().setCities(cities);
		setCitySelectionVisible(true);
	}

	public void initCinemas() {
		setCinemaSelectionVisible(true);
		Filter filter = prepareCinemaFilter();
		this.cinemas = ApiHelper.getCinemas(filter);
	}

	public void initMovies() {
		setMovieSelectionVisible(true);

		Filter filter = prepareMovieFilter();
		this.movies = ApiHelper.getMoviesInCinema(filter);
		MovieHelper.verifyList(movies, null);
		/*
		 * System.out.println("*****MOVIES*****"); movies.showAllElements();
		 * System.out.println("**********");
		 */
		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		/*
		 * System.out.println("*****MOVIES_POSTER*****");
		 * moviePosters.showAllElements(); System.out.println("**********");
		 */
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	public void initShowtimes() {
		Filter filter = prepareShowtimeFilter();
		this.showtimes = ApiHelper.getMovieShowtimesInCinema(filter);
		setShowtimeSelectionVisible(true);

		/*
		 * List<ShowtimeDay> showtimeDayList = showtimes.getNormalizeList(); for
		 * (ShowtimeDay sd : showtimeDayList) { System.out.println(sd.getDate()); for
		 * (String h : sd.getHours()) { System.out.println(h); } }
		 */

	}

	private Filter prepareCinemaFilter() {
		Filter filter = new Filter();
		filter.addFilterParam(Filter.Parameter.LOCATION, selectedCity.getLat() + "," + selectedCity.getLon());
		filter.addFilterParam(Filter.Parameter.DISTANCE, "10");
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareMovieFilter() {
		Filter filter = new Filter();
		filter.addQueryParam(Filter.Query.CINEMA_ID, selectedCinema.getId().toString());
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareShowtimeFilter() {
		Filter filter = new Filter();
		filter.addQueryParam(Filter.Query.CINEMA_ID, selectedCinema.getId().toString());
		filter.addQueryParam(Filter.Query.MOVIE_ID, selectedMovie.getId().toString());
		return filter;
	}

	public List<City> getCitiesList(String prefix) {
		List<City> returnList = new ArrayList<City>();
		if (prefix.length() > 0) {
			for (City city : getCities().getList()) {
				if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(city);
				}
			}
		} else {
			returnList.addAll(getCities().getList());
		}
		return returnList;
	}

	public City findCity(String prefix) {
		for (City city : getCities().getList()) {
			if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
				return city;
			}
		}
		return null;
	}

	public void selectCity(SelectEvent event) {
		setCitySelectionVisible(false);
		initCinemas();
	}

	public void selectCityQuick() {
		setCitySelectionVisible(false);
		initCinemas();
	}

	public void selectCinema(SelectEvent selectEvent) {
		setCinemaSelectionVisible(false);
		initMovies();
	}

	public void selectMovie(SelectEvent selectEvent) {
		setMovieSelectionVisible(false);
		initShowtimes();
	}

	public void selectMovie() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String movieId = ec.getRequestParameterMap().get("movieId");
		selectedMovie = movies.findMovie(Long.valueOf(movieId));
		setMovieSelectionVisible(false);
		initShowtimes();
	}

	public void selectShowtime() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String link = externalContext.getRequestParameterMap().get("link");
		PrimeFaces.current().executeScript("window.open('" + link + "', '_newtab')");
	}

	public void showMovieDetail() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void firstStepClicked() {
		setCitySelectionVisible(true);
		setCinemaSelectionVisible(false);
		setMovieSelectionVisible(false);
		setShowtimeSelectionVisible(false);
	}

	public void secondStepClicked() {
		setCitySelectionVisible(false);
		setCinemaSelectionVisible(true);
		setMovieSelectionVisible(false);
		setShowtimeSelectionVisible(false);
	}

	public void thirdStepClicked() {
		setCitySelectionVisible(false);
		setCinemaSelectionVisible(false);
		setMovieSelectionVisible(true);
		setShowtimeSelectionVisible(false);
	}

	private void createCitiesQuickSelection() {
		citiesQuickList = new ArrayList<City>();
		long startTime = System.currentTimeMillis();

		citiesQuickList.add(findCity("bydgoszcz"));
		citiesQuickList.add(findCity("gdańsk"));
		citiesQuickList.add(findCity("gdynia"));
		citiesQuickList.add(findCity("katowice"));
		citiesQuickList.add(findCity("krakow"));
		citiesQuickList.add(findCity("poznań"));
		citiesQuickList.add(findCity("rzeszow"));
		citiesQuickList.add(findCity("szczecin"));
		citiesQuickList.add(findCity("torun"));
		citiesQuickList.add(findCity("warszawa"));
		citiesQuickList.add(findCity("wrocław"));
		citiesQuickList.add(findCity("zielona gora"));

		long stopTime = System.currentTimeMillis();
		System.out.println("Cities find in " + ((stopTime - startTime) / 1000) + "second");
	}

	public Cities getCities() {
		if (cities == null) {
			initCities();
		}
		return cities;
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

	public Cinema getSelectedCinema() {
		return selectedCinema;
	}

	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public List<Cinema> getCinemaList() {
		return cinemas.getList();
	}

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}

	public List<ShowtimeDay> getShowtimeDayList() {
		return showtimes.getNormalizeList();
	}

	public List<Showtime> getShowtimeList() {
		return showtimes.getList();
	}

	public List<City> getCitiesQuickList() {
		return citiesQuickList;
	}

	public boolean isCitySelectionVisible() {
		return citySelectionVisible;
	}

	public void setCitySelectionVisible(boolean citySelectionVisible) {
		this.citySelectionVisible = citySelectionVisible;
	}

	public boolean isCinemaSelectionVisible() {
		return cinemaSelectionVisible;
	}

	public void setCinemaSelectionVisible(boolean cinemaSelectionVisible) {
		this.cinemaSelectionVisible = cinemaSelectionVisible;
	}

	public boolean isMovieSelectionVisible() {
		return movieSelectionVisible;
	}

	public void setMovieSelectionVisible(boolean movieSelectionVisible) {
		this.movieSelectionVisible = movieSelectionVisible;
	}

	public boolean isShowtimeSelectionVisible() {
		return showtimeSelectionVisible;
	}

	public void setShowtimeSelectionVisible(boolean showtimeSelectionVisible) {
		this.showtimeSelectionVisible = showtimeSelectionVisible;
	}

}