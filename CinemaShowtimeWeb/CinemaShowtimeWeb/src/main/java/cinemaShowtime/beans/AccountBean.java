package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.database.dao.AccountPreferenceItemDAO;
import cinemaShowtime.database.model.Account;
import cinemaShowtime.database.model.AccountPreference;
import cinemaShowtime.database.model.AccountPreferenceItem;
import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.utils.Application;
import cinemaShowtime.utils.Const;
import cinemaShowtime.utils.Logger;
import model.json.City;
import model.json.cinema.Cinema;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;

@ManagedBean(name = "accountBean", eager = true)
@SessionScoped
public class AccountBean {

	private Account account;

	private List<Genre> genreList;
	private List<Genre> selectedGenreList;

	private Movies movies;
	private Cities cities;
	private Cinemas cinemas;

	private Movie movieToAdd;
	private List<Movie> selectedMovieList;

	private City selectedCity;
	private List<Cinema> selectedCinemaList;

	private boolean genreSelectionVisible;
	private boolean cinemaSelectionVisible;
	private boolean movieSelectionVisible;
	private boolean summaryVisible;

	private AccountPreferenceItemDAO accountPreferenceDAO = new AccountPreferenceItemDAO();

	public AccountBean() {
		this.account = Application.getInstance().getAccount();

		prepareAccountPreference();

		setGenreSelectionVisible(true);
		initGenres();
	}

	private void prepareAccountPreference() {
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("accountID", account.getId());
		List<AccountPreferenceItem> accPreferenceList = accountPreferenceDAO.findList(queryParamMap);
		AccountPreference accPreference = new AccountPreference();
		ArrayList<Integer> cinemaIds = new ArrayList<Integer>();
		ArrayList<Integer> genreIds = new ArrayList<Integer>();

		for (AccountPreferenceItem item : accPreferenceList) {
			accPreference.setAccountID(item.getAccountID());
			if (item.getGenreID() != null) {
				genreIds.add(item.getGenreID());
			}
			if (item.getCinemaID() != null) {
				cinemaIds.add(item.getCinemaID());
			}
		}
		Integer[] integerArray;
		integerArray = Arrays.copyOf(genreIds.toArray(), genreIds.toArray().length, Integer[].class);
		accPreference.setGenreIds(integerArray);
		integerArray = Arrays.copyOf(cinemaIds.toArray(), genreIds.toArray().length, Integer[].class);
		accPreference.setCinemaIds(integerArray);
	}

	private void initGenres() {
		genreList = ApiHelper.getGenres().removeNullGenres().getList();
		Application.getInstance().setGenreList(genreList);
		selectedGenreList = new ArrayList<Genre>();

	}

	private void initMovies() {
		ApiFilter filter = prepareMovieFilter();
		this.movies = ApiHelper.getMovies(filter); // TO DO ładowanie wszystkich filmów po tokenie strony
		// MovieHelper.verifyList(movies, null, null);
		/*
		 * filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		 * filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS); Movies moviePosters =
		 * ApiHelper.getMoviesPosterEngishVersion(filter); moviePosters.fillMovieMap();
		 * MovieHelper.addPosterToMovie(movies, moviePosters);
		 */
		Application.getInstance().setMovies(movies);

		selectedMovieList = new ArrayList<Movie>();
	}

	public void initCities() {
		this.cities = ApiHelper.getCitiesFromApi();
		Application.getInstance().setCities(cities);
	}

	public void initCinemas() {
		ApiFilter filter = prepareCinemaFilter();
		this.cinemas = ApiHelper.getCinemas(filter);
		Application.getInstance().setCinemas(cinemas);
	}

	private ApiFilter prepareMovieFilter() {
		ApiFilter filter = new ApiFilter();
		filter.setFields(ApiFilter.Field.MOVIE_SHORT_FIELDS);
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Const.COUNTRIES);
		filter.addFilterParam(ApiFilter.Parameter.INCLUDE_OUTDATED, "true");
		filter.addFilterParam(ApiFilter.Parameter.PAGE_SIZE, "100");
		return filter;
	}

	private ApiFilter prepareCinemaFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.LOCATION, selectedCity.getLat() + "," + selectedCity.getLon());
		filter.addFilterParam(ApiFilter.Parameter.DISTANCE, "10");
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		return filter;
	}

	public void addMovie() {
		if (movieToAdd != null && selectedMovieList.size() < 5) {
			selectedMovieList.add(movieToAdd);
			movieToAdd = null;
		}
	}

	public void savePreference() {
		// TO DO
		Logger.log("ZAPISUJE PREFERECJE");
	}

	public void selectCity(SelectEvent event) {
		initCinemas();
	}

	public List<Movie> getMovieList(String prefix) {
		List<Movie> returnList = new ArrayList<Movie>();
		if (prefix.length() > 0) {
			for (Movie movie : movies.getList()) {
				if (movie.getTitle().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(movie);
				}
			}
		} else {
			returnList.addAll(movies.getList());
		}
		return returnList;
	}

	public List<City> getCityList(String prefix) {
		List<City> returnList = new ArrayList<City>();
		if (prefix.length() > 0) {
			for (City city : cities.getList()) {
				if (city.getName().toLowerCase().contains(prefix.toLowerCase())) {
					returnList.add(city);
				}
			}
		} else {
			returnList.addAll(cities.getList());
		}
		return returnList;
	}

	public List<Cinema> getCinemaList() {
		if (cinemas != null) {
			return cinemas.getList();
		}
		return null;

	}

	public void goToNextStep() {
		if (genreSelectionVisible) {
			initCities();
			setMovieSelectionVisible(false);
			setCinemaSelectionVisible(true);
			// initMovies();
			setGenreSelectionVisible(false);
			setSummaryVisible(false);
		} else if (movieSelectionVisible) {
			// pominiety wybor filmow
		} else if (cinemaSelectionVisible) {
			setGenreSelectionVisible(false);
			setMovieSelectionVisible(false);
			setCinemaSelectionVisible(false);
			setSummaryVisible(true);
		}
	}

	public void goToHomePage() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect("/CinemaShowtimeWeb/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void firstStepClicked() {
		setGenreSelectionVisible(true);
		setMovieSelectionVisible(false);
		setCinemaSelectionVisible(false);
		setSummaryVisible(false);
	}

	public void secondStepClicked() {
		setGenreSelectionVisible(false);
		setMovieSelectionVisible(true);
		setCinemaSelectionVisible(false);
		setSummaryVisible(false);
	}

	public void thirdStepClicked() {
		setGenreSelectionVisible(false);
		setMovieSelectionVisible(false);
		setCinemaSelectionVisible(true);
		setSummaryVisible(false);
	}

	public Account getAccount() {
		return account;
	}

	public List<Genre> getGenreList() {
		return genreList;
	}

	public List<Genre> getSelectedGenreList() {
		return selectedGenreList;
	}

	public void setSelectedGenreList(List<Genre> selectedGenreList) {
		this.selectedGenreList = selectedGenreList;
	}

	public List<Movie> getSelectedMovieList() {
		return selectedMovieList;
	}

	public void setSelectedMovieList(List<Movie> selectedMovieList) {
		this.selectedMovieList = selectedMovieList;
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

	public List<Cinema> getSelectedCinemaList() {
		return selectedCinemaList;
	}

	public void setSelectedCinemaList(List<Cinema> selectedCinemaList) {
		this.selectedCinemaList = selectedCinemaList;
	}

	public Movie getMovieToAdd() {
		return movieToAdd;
	}

	public void setMovieToAdd(Movie movieToAdd) {
		this.movieToAdd = movieToAdd;
	}

	public boolean isGenreSelectionVisible() {
		return genreSelectionVisible;
	}

	public void setGenreSelectionVisible(boolean genreSelectionVisible) {
		this.genreSelectionVisible = genreSelectionVisible;
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

	public boolean isSummaryVisible() {
		return summaryVisible;
	}

	public void setSummaryVisible(boolean summaryVisible) {
		this.summaryVisible = summaryVisible;
	}

}
