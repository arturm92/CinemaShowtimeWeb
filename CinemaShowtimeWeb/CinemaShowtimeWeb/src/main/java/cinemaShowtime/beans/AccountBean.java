package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import model.json.cinema.comparator.CinemaNameComparator;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Genres;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;

@ManagedBean(name = "accountBean", eager = true)
@ViewScoped
public class AccountBean {

	private Account account;
	private AccountPreference accountPreference;

	private List<Genre> selectedGenreList;

	private Genres genres;
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
		initGenres();
		initCities();
		this.account = Application.getInstance().getAccount();

		accountPreference = prepareAccountPreference();
		if (accountPreference != null) {
			for (Integer genreId : accountPreference.getGenreIds()) {
				if (genreId != null) {
					selectedGenreList.add(genres.findGenreById(genreId));
				}
			}
			if (accountPreference.getCityId() != null) {
				this.selectedCity = cities.findCityById(accountPreference.getCityId());
			}
		}
		setGenreSelectionVisible(true);
	}

	private AccountPreference prepareAccountPreference() {
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("accountId", account.getId());
		List<AccountPreferenceItem> itemList = accountPreferenceDAO.findList(queryParamMap);
		if (itemList != null && itemList.size() > 0) {
			AccountPreference accPreference = new AccountPreference();
			ArrayList<Integer> cinemaIds = new ArrayList<Integer>();
			ArrayList<Integer> genreIds = new ArrayList<Integer>();

			for (AccountPreferenceItem item : itemList) {
				accPreference.setAccountId(item.getAccountId());
				if (item.getCityId() != null) {
					accPreference.setCityId(item.getCityId());
				}
				if (item.getGenreId() != null) {
					genreIds.add(item.getGenreId());
				}
				if (item.getCinemaId() != null) {
					cinemaIds.add(item.getCinemaId());
				}
			}
			Integer[] integerArray;
			integerArray = Arrays.copyOf(genreIds.toArray(), genreIds.toArray().length, Integer[].class);
			accPreference.setGenreIds(integerArray);
			integerArray = Arrays.copyOf(cinemaIds.toArray(), genreIds.toArray().length, Integer[].class);
			accPreference.setCinemaIds(integerArray);
			return accPreference;
		} else {
			return null;
		}
	}

	private void initGenres() {
		genres = ApiHelper.getGenres().removeNullGenres();
		Collections.sort(getGenreList());
		Application.getInstance().setGenreList(getGenreList());
		selectedGenreList = new ArrayList<Genre>();

	}

	public void savePreference() {
		Logger.log("ZAPISUJE PREFERENCJE");
		//usuwa wszystkie poprzednie preferencje
		accountPreferenceDAO.delete(new AccountPreferenceItem(account.getId(), null, null, null));
		//zapisuje aktualne
		List<AccountPreferenceItem> itemList = new ArrayList<AccountPreferenceItem>();
		for (Genre genre : selectedGenreList) {
			itemList.add(new AccountPreferenceItem(account.getId(), genre.getId().intValue(), null, null));
		}
		for (Cinema cinema : selectedCinemaList) {
			itemList.add(new AccountPreferenceItem(account.getId(), null, cinema.getId().intValue(), null));
		}
		if (selectedCity != null) {
			itemList.add(new AccountPreferenceItem(account.getId(), null, null, selectedCity.getId().intValue()));
		}
		for (AccountPreferenceItem item : itemList) {
			Integer accountPreferenceId = accountPreferenceDAO.insert(item);
		}
	}

	public void initCities() {
		this.cities = ApiHelper.getCitiesFromApi();
		Application.getInstance().setCities(cities);
	}

	public void initCinemas() {
		ApiFilter filter = prepareCinemaFilter();
		this.cinemas = ApiHelper.getCinemas(filter);
		Collections.sort(getCinemaList(), new CinemaNameComparator());
		Application.getInstance().setCinemas(cinemas);
		selectedCinemaList = new ArrayList<Cinema>();
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
			setMovieSelectionVisible(false);
			setCinemaSelectionVisible(true);
			setGenreSelectionVisible(false);
			setSummaryVisible(false);

			if (selectedCity != null) {
				initCinemas();
				for (Integer cinemaId : accountPreference.getCinemaIds()) {
					if (cinemaId != null) {
						selectedCinemaList.add(cinemas.findCinemaById(cinemaId));
					}
				}
			}
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
		return genres.getList();
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
