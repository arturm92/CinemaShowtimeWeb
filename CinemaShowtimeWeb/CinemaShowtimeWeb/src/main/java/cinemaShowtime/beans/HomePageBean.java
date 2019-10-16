package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import cinemaShowtime.database.model.AccountPreference;
import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.filters.ReloadInterface;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.Application;
import cinemaShowtime.utils.Const;
import cinemaShowtime.utils.DateFormater;
import cinemaShowtime.utils.Logger;
import model.json.City;
import model.json.Showtime;
import model.json.cinema.Cinema;
import model.json.cinema.LocationApi;
import model.json.cinema.comparator.CinemaNameComparator;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.Genre;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "homePageBean", eager = true)
@ViewScoped
public class HomePageBean implements ReloadInterface {

	private City currentCity;

	private Cities cities;
	private Cinemas cinemas;
	private Movies movies;
	private Movies moviePosters;
	private Showtimes showtimes;
	private Cinema selectedCinema;
	private int distance = Const.DISTANCE;
	private String timeTo;
	private int index;
	private boolean allGenres = false;

	public HomePageBean() {
		try {
			long startTime = System.currentTimeMillis();

			initCities();
			reloadPage();

			long stopTime = System.currentTimeMillis();
			Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		allGenres = false;
		prepareCinemas();
		customSortCinemas();
		selectedCinema = getFilteredCinemaList().get(index);
		prepareMovies();
		prepareShowtimes();
	}

	@Override
	public void reloadPage() {
		AccountPreference accountPreference = Application.getInstance().getAccountPreference();
		if (Application.getInstance().isPreferenceHelp() && accountPreference != null) {
			if (accountPreference.getCityId() != null) {
				currentCity = cities.findCityById(accountPreference.getCityId());
			}
		}
		init();
	}

	public void initCities() {
		cities = Application.getInstance().getCities();
		if (cities == null) {
			cities = ApiHelper.getCities();
			Application.getInstance().setCities(cities);
		}
	}

	private void prepareCinemas() {
		ApiFilter filter = prepareCinemaFilter();
		cinemas = ApiHelper.getCinemas(filter);
		if (cinemas.getList().isEmpty()) {
			distance += 25;
			prepareCinemas();
		}
	}

	public List<Cinema> getFilteredCinemaList() {
		AccountPreference accountPreference = Application.getInstance().getAccountPreference();
		if (Application.getInstance().isPreferenceHelp() && accountPreference != null) {
			List<Cinema> retrunCinemaList = new ArrayList<Cinema>();
			Long[] cinemaIds = accountPreference.getCinemaIds();
			if (cinemaIds.length > 0) {
				for (Cinema cinema : cinemas.getList()) {
					for (int i = 0; i < cinemaIds.length; i++) {
						if (cinema.getId().compareTo(cinemaIds[i]) == 0) {
							retrunCinemaList.add(cinema);
						}
					}
				}
			}
			return retrunCinemaList;
		} else {
			return cinemas.getList();
		}
	}

	private void prepareMovies() {
		ApiFilter filter = prepareMoviesInCinema(2);
		MovieHelper.verifyList(movies, null, null);
		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	public List<MovieFormatted> getFilteredMovieList() {
		AccountPreference accountPreference = Application.getInstance().getAccountPreference();
		if (Application.getInstance().isPreferenceHelp() && accountPreference != null && !isAllGenres()) {
			List<MovieFormatted> retrunMovieList = new ArrayList<MovieFormatted>();
			Long[] genreIds = accountPreference.getGenreIds();
			if (genreIds.length > 0) {
				for (MovieFormatted movie : movies.getList()) {
					boolean stop = false;
					List<Genre> list = movie.getGenre();
					for (Genre genre : list) {
						for (int i = 0; i < genreIds.length; i++) {
							if (genre.getId().compareTo(genreIds[i]) == 0) {
								retrunMovieList.add(movie);
								stop = true;
								break;
							}
						}
						if (stop) {
							break;
						}
					}
				}
			}
			return retrunMovieList;
		} else {
			return movies != null ? movies.getList() : null;
		}
	}

	private ApiFilter prepareMoviesInCinema(int days) {
		if (days > 30) {
			index++;
			selectedCinema = getFilteredCinemaList().get(index);
			days = 2;
		}
		DateFormater df = new DateFormater();
		timeTo = df.convertSimpleDateToTimezone(df.getDaysFromToday(days));
		ApiFilter filter = prepareMovieFilter();
		movies = ApiHelper.getMoviesInCinema(filter);
		if (movies.getList().isEmpty()) {
			days += 5;
			filter = prepareMoviesInCinema(days);
		}
		return filter;
	}

	private void prepareShowtimes() {
		ApiFilter filter = prepareShowtimeFilter();
		showtimes = ApiHelper.getMovieShowtimesInCinema(filter);
		for (MovieFormatted movie : movies.getList()) {
			List<Showtime> movieShowtime = showtimes.findMovieShowtime(movie.getId());
			Showtimes tmp = new Showtimes();
			tmp.setList(movieShowtime);
			movie.setShowtimeDayList(tmp.getNormalizeList());
		}
	}

	private ApiFilter prepareCinemaFilter() {
		ApiFilter filter = new ApiFilter();
		LocationApi locationApi = Application.getInstance().getLocationApi();
		if (currentCity == null) {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION,
					locationApi.getLatitude() + "," + locationApi.getLongitude());
		} else {
			filter.addFilterParam(ApiFilter.Parameter.LOCATION, currentCity.getLat() + "," + currentCity.getLon());
		}
		filter.addFilterParam(ApiFilter.Parameter.DISTANCE, String.valueOf(Const.DISTANCE));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		return filter;
	}

	private ApiFilter prepareMovieFilter() {
		DateFormater df = new DateFormater();
		ApiFilter filter = new ApiFilter();
		filter.addQueryParam(ApiFilter.Query.CINEMA_ID, getSelectedCinema().getId().toString());
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(ApiFilter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(ApiFilter.Parameter.TIME_TO, timeTo);
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		return filter;
	}

	private ApiFilter prepareShowtimeFilter() {
		DateFormater df = new DateFormater();
		ApiFilter filter = new ApiFilter();
		filter.addQueryParam(ApiFilter.Query.CINEMA_ID, selectedCinema.getId().toString());
		filter.addFilterParam(ApiFilter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(ApiFilter.Parameter.TIME_TO, timeTo);
		return filter;
	}

	public void selectCinema() {
		prepareMovies();
		prepareShowtimes();
	}

	public void clickMovie() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectShowtime() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String link = externalContext.getRequestParameterMap().get("link");
		PrimeFaces.current().executeScript("window.open('" + link + "', '_newtab')");
	}

	private void customSortCinemas() {
		List<Cinema> premiumCinemas = new ArrayList<Cinema>();
		List<Cinema> others = new ArrayList<Cinema>();
		for (Cinema cinema : cinemas.getList()) {
			if (cinema.getName().contains("Multikino") || cinema.getName().contains("Cinema City")) {
				premiumCinemas.add(cinema);
			} else {
				others.add(cinema);
			}
		}
		Collections.sort(premiumCinemas, new CinemaNameComparator());
		Collections.sort(others, new CinemaNameComparator());
		premiumCinemas.addAll(others);
		cinemas.setList(premiumCinemas);
	}

	public Cinemas getCinemas() {
		return cinemas;
	}

	public List<Cinema> getCinemaList() {
		return cinemas.getList();
	}

	public Movies getMovies() {
		return movies;
	}

	public Cinema getSelectedCinema() {
		return selectedCinema;
	}

	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

	public boolean isAllGenres() {
		return allGenres;
	}

	public void setAllGenres(boolean allGenres) {
		try {
			int size = Application.getInstance().getAccountPreference().getGenreIds().length;
			this.allGenres = allGenres;
		} catch (NullPointerException e) {
			this.allGenres = true;
		}
	}

	public boolean isCheckboxRendered() {
		return Application.getInstance().getAccount() != null ? true : false;
	}

}