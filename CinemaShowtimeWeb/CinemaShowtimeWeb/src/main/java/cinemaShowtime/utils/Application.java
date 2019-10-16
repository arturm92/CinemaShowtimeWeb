package cinemaShowtime.utils;

import cinemaShowtime.database.model.Account;
import cinemaShowtime.database.model.AccountPreference;
import model.json.cinema.LocationApi;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Genres;
import model.json.complex.Movies;

public class Application {

	private static Application instance = null;
	private Account account;
	private Movies movies;
	private Cities cities;
	private Cinemas cinemas;
	private Genres genres;
	private AccountPreference accountPreference;
	private boolean preferenceHelp = false;
	private LocationApi locationApi;

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}

	public Cities getCities() {
		return cities;
	}

	public Cinemas getCinemas() {
		return cinemas;
	}

	public void setCinemas(Cinemas cinemas) {
		this.cinemas = cinemas;
	}

	public Genres getGenres() {
		return genres;
	}

	public void setGenres(Genres genres) {
		this.genres = genres;
	}

	public Movies getMovies() {
		return movies;
	}

	public void setMovies(Movies movies) {
		this.movies = movies;
	}

	public AccountPreference getAccountPreference() {
		return accountPreference;
	}

	public void setAccountPreference(AccountPreference accountPreference) {
		this.accountPreference = accountPreference;
	}

	public boolean isPreferenceHelp() {
		return preferenceHelp;
	}

	public void setPreferenceHelp(boolean preferenceHelp) {
		this.preferenceHelp = preferenceHelp;
	}

	public void setLocationApi(LocationApi locationApi) {
		this.locationApi = locationApi;

	}

	public LocationApi getLocationApi() {
		return locationApi;
	}

}