package cinemaShowtime.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.LocationApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.Application;
import cinemaShowtime.utils.Consts;
import cinemaShowtime.utils.DateFormater;
import model.json.City;
import model.json.Showtime;
import model.json.ShowtimeDay;
import model.json.cinema.Cinema;
import model.json.cinema.LocationApi;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Showtimes;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

public class MovieDetailBean {

	private static MovieDetailBean instance = null;
	private MovieFormatted movie;

	private Cities cities;
	private Cinemas cinemas;
	private Showtimes showtimes;
	private Showtimes displayShowtimes;

	private int distance = Consts.DISTANCE;
	private int days = 7;
	private City selectedCity;
	private Cinema selectedCinema;
	private LocationApi locationApi;

	public static MovieDetailBean getInstance() {
		if (instance == null) {
			instance = new MovieDetailBean();
		}
		return instance;
	}

	public void initMovieDetailBean(String movieId) {
		movie = ApiHelper.getMovieMultimedia(Long.valueOf(movieId));
		MovieFormatted movieDescripstion = ApiHelper.getMovieDescription(movie.getId());
		MovieHelper.mergeMovieDetails(movie, movieDescripstion);

		initLocationApi();
		initCities();
		resetDistance();
		initCinemas();
		if (!cinemas.getList().isEmpty()) {
			selectedCity = cities.findCityByName(getSelectedCinema().getLoaction().getAddress().getCity());
			Application.getInstance().setCinemas(cinemas);
			cinemas.showAllElements();
			initShowtimes();
			showtimes.showAllElements();
		}
	}
	
	private void initLocationApi() {
		locationApi = LocationApiHelper.getLocation();
	}

	private void initCities() {
		cities = ApiHelper.getCitiesFromApi();
		Application.getInstance().setCities(cities);
	}

	private int initCinemas() {
		if (distance > 50) {
			return -1;
		}
		ApiFilter filter = prepareCinemaFilter();
		cinemas = ApiHelper.getCinemas(filter);
		if (cinemas.getList().isEmpty()) {
			distance += 25;
			initCinemas();
		}
		return 1;
	}

	public void initShowtimes() {
		ApiFilter filter = prepareShowtimeFilter();
		showtimes = ApiHelper.getMovieShowtimesInCinema(filter);
		filterShowtimes();
	}
	
	private void resetDistance() {
		this.distance = Consts.DISTANCE;
	}

	public void selectCity(SelectEvent event) {
		initCinemas();
		Application.getInstance().setCinemas(cinemas);
		initShowtimes();
	}

	public void selectCinema(SelectEvent selectEvent) {
		filterShowtimes();
	}

	public void goToWebsite() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String link = externalContext.getRequestParameterMap().get("link");
		PrimeFaces.current().executeScript("window.open('" + link + "', '_newtab')");
	}

	private void filterShowtimes() {
		displayShowtimes = new Showtimes();
		List<Showtime> filteredList = new ArrayList<Showtime>();
		for (Showtime showtime : showtimes.getList()) {
			if (showtime.getCinemaId().compareTo(getSelectedCinema().getId()) == 0) {
				System.out.println(showtime.getCinemaId());
				filteredList.add(showtime);
			}
		}
		displayShowtimes.setList(filteredList);
	}

	private ApiFilter prepareCinemaFilter() {
		ApiFilter filter = new ApiFilter();
		DateFormater df = new DateFormater();
		if (selectedCity != null) {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION, selectedCity.getLat() + "," + selectedCity.getLon());
		} else {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION,
					locationApi.getLatitude() + "," + locationApi.getLongitude());
		}

		filter.addFilterParam(ApiFilter.Parameter.DISTANCE, String.valueOf(distance));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(ApiFilter.Query.MOVIE_ID, movie.getId().toString());
		filter.addFilterParam(ApiFilter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(ApiFilter.Parameter.TIME_TO, df.convertSimpleDateToTimezone(df.getDaysFromToday(days)));
		return filter;
	}

	private ApiFilter prepareShowtimeFilter() {
		ApiFilter filter = new ApiFilter();
		DateFormater df = new DateFormater();
		if (selectedCity != null) {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION, selectedCity.getLat() + "," + selectedCity.getLon());
		} else {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION,
					locationApi.getLatitude() + "," + locationApi.getLongitude());
		}
		filter.addFilterParam(ApiFilter.Parameter.DISTANCE, String.valueOf(distance));
		filter.addQueryParam(ApiFilter.Query.MOVIE_ID, movie.getId().toString());
		filter.addFilterParam(ApiFilter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(ApiFilter.Parameter.TIME_TO, df.convertSimpleDateToTimezone(df.getDaysFromToday(days)));
		return filter;
	}

	public List<City> getCityFilterList(String prefix) {
		List<City> returnList = new ArrayList<City>();
		if (prefix.length() > 0) {
			for (City city : Application.getInstance().getCities().getList()) {
				if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(city);
				}
			}
		} else {
			returnList.addAll(Application.getInstance().getCities().getList());
		}
		return returnList;
	}

	public List<Cinema> getCinemaFilterList(String prefix) {
		List<Cinema> returnList = new ArrayList<Cinema>();
		if (prefix.length() > 0) {
			for (Cinema cinema : getCinemaList()) {
				if (cinema.getName().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(cinema);
				}
			}
		} else {
			returnList.addAll(getCinemaList());
		}
		return returnList;
	}

	public Movie getMovie() {
		return movie;
	}

	public List<Cinema> getCinemaList() {
		return cinemas.getList();
	}

	public List<ShowtimeDay> getShowtimeDayList() {
		if (displayShowtimes != null) {
			return displayShowtimes.getNormalizeList();
		} else {
			return null;
		}
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

	public Cinema getSelectedCinema() {
		if (selectedCinema == null && !cinemas.getList().isEmpty()) {
			return cinemas.getList().get(0);
		}
		return selectedCinema;
	}

	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

	public boolean isRenderedShowtime() {
		if (displayShowtimes != null) {
			return true;
		} else {
			return false;
		}
	}
}
