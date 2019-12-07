package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.database.dao.AccountPreferenceItemDAO;
import cinemaShowtime.database.model.Account;
import cinemaShowtime.database.model.AccountPreference;
import cinemaShowtime.database.model.AccountPreferenceItem;
import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.filters.ReloadInterface;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.utils.Application;
import cinemaShowtime.utils.AppParameter;
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
@SessionScoped
public class AccountBean implements ReloadInterface {

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
	private boolean summaryVisible;

	private String message = "";

	private AccountPreferenceItemDAO accountPreferenceDAO = new AccountPreferenceItemDAO();

	private boolean preferenceHelp = false;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public AccountBean() {
		Logger.logCreateBeanInfo("AccountBean");
	}

	@PostConstruct
	public void init() {
		long startTime = System.currentTimeMillis();
		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	public void loadPreferences() {
		this.account = loginBean.getLoggedAccount();
		if (account != null) {
			initGenres();
			initCities();
			accountPreference = getAccountPreferece();
			if (accountPreference != null) {
				preferenceHelp = true;
				for (Long genreId : accountPreference.getGenreIds()) {
					if (genreId != null) {
						selectedGenreList.add(genres.findGenreById(genreId));
					}
				}
				if (accountPreference.getCityId() != null) {
					this.selectedCity = cities.findCityById(accountPreference.getCityId());
				}
			}
			setGenreSelectionVisible(true);
		} else {
			clear();
			message = "Aby przeglądać swoje konto musisz być zalogowany. Zaloguj się!";
		}
	}

	private void clear() {
		account = null;
		preferenceHelp = false;
		accountPreference = null;
		selectedGenreList = null;
		selectedCinemaList = null;
		selectedCity = null;
		setGenreSelectionVisible(false);
	}

	public AccountPreference getAccountPreferece() {
		if (account != null && accountPreference == null) {
			prepareAccountPreference();
		}
		return accountPreference;
	}

	public void prepareAccountPreference() {
		AccountPreferenceItemDAO accountPreferenceItemDAO = new AccountPreferenceItemDAO();
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("accountId", account.getId());
		List<AccountPreferenceItem> itemList = accountPreferenceItemDAO.findList(queryParamMap);
		if (itemList != null && itemList.size() > 0) {
			AccountPreference accPreference = new AccountPreference();
			ArrayList<Long> cinemaIds = new ArrayList<Long>();
			ArrayList<Long> genreIds = new ArrayList<Long>();

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
			Long[] longArray;
			longArray = Arrays.copyOf(genreIds.toArray(), genreIds.toArray().length, Long[].class);
			accPreference.setGenreIds(longArray);
			longArray = Arrays.copyOf(cinemaIds.toArray(), genreIds.toArray().length, Long[].class);
			accPreference.setCinemaIds(longArray);

			this.accountPreference = accPreference;
		}
	}

	private void initGenres() {
		genres = Application.getInstance().getGenres();
		if (genres == null) {
			genres = ApiHelper.getGenres().removeNullGenres();
			Application.getInstance().setGenres(genres);
		}
		Collections.sort(getGenreList());
		selectedGenreList = new ArrayList<Genre>();

	}

	public void savePreference() {
		Logger.log("ZAPISUJE PREFERENCJE");
		accountPreferenceDAO.delete(new AccountPreferenceItem(account.getId(), null, null, null));
		List<AccountPreferenceItem> itemList = new ArrayList<AccountPreferenceItem>();
		for (Genre genre : selectedGenreList) {
			itemList.add(new AccountPreferenceItem(account.getId(), genre.getId(), null, null));
		}
		for (Cinema cinema : selectedCinemaList) {
			itemList.add(new AccountPreferenceItem(account.getId(), null, cinema.getId(), null));
		}
		if (selectedCity != null) {
			itemList.add(new AccountPreferenceItem(account.getId(), null, null, selectedCity.getId()));
		}
		for (AccountPreferenceItem item : itemList) {
			Long accountPreferenceId = accountPreferenceDAO.insert(item);
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Udało się!", "Preferencje zapisano w systemie"));
		
		prepareAccountPreference();
		firstStepClicked();

	}

	public void initCities() {
		cities = Application.getInstance().getCities();
		if (cities == null) {
			this.cities = ApiHelper.getCities();
			Application.getInstance().setCities(cities);
		}
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
		filter.addFilterParam(ApiFilter.Parameter.LANG, AppParameter.LANGUAGE);
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
			setCinemaSelectionVisible(true);
			setGenreSelectionVisible(false);
			setSummaryVisible(false);

			if (selectedCity != null) {
				initCinemas();
				for (Long cinemaId : accountPreference.getCinemaIds()) {
					if (cinemaId != null) {
						selectedCinemaList.add(cinemas.findCinemaById(cinemaId));
					}
				}
			}
		} else if (cinemaSelectionVisible) {
			setGenreSelectionVisible(false);
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
		setCinemaSelectionVisible(false);
		setSummaryVisible(false);
	}

	public void secondStepClicked() {
		setGenreSelectionVisible(false);
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

	public boolean isSummaryVisible() {
		return summaryVisible;
	}

	public void setSummaryVisible(boolean summaryVisible) {
		this.summaryVisible = summaryVisible;
	}

	@Override
	public void reloadPage() {
		init();
		if (account == null) {
			setCinemaSelectionVisible(false);
			setGenreSelectionVisible(false);
			setSummaryVisible(false);
		} else {
			setCinemaSelectionVisible(false);
			setGenreSelectionVisible(true);
			setSummaryVisible(false);
		}
	}

	public String getMessage() {
		return message;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean isPreferenceHelp() {
		return preferenceHelp;
	}

	public void setPreferenceHelp(boolean preferenceHelp) {
		this.preferenceHelp = preferenceHelp;
	}
}
