package cinemaShowtime.utils;

import java.util.List;

import cinemaShowtime.database.model.Account;
import cinemaShowtime.database.model.AccountPreference;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Movies;
import model.json.movie.Genre;

public class Application {

	private static Application instance = null;
	private Account account;
	private Movies movies;
	private Cities cities;
	private Cinemas cinemas;
	private List<Genre> genreList;
	private AccountPreference accountPreference;
	private boolean preferenceHelp = false;

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

	public List<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(List<Genre> genreList) {
		this.genreList = genreList;
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

}