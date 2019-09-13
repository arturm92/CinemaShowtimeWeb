package cinemaShowtime.database.model;

public class AccountPreference {

	private int accountID;
	private Integer[] genreIds;
	private Integer[] cinemaIds;

	public AccountPreference() {
		this.genreIds = new Integer[] {};
		this.cinemaIds = new Integer[] {};
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public Integer[] getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(Integer[] genreIds) {
		this.genreIds = genreIds;
	}

	public Integer[] getCinemaIds() {
		return cinemaIds;
	}

	public void setCinemaIds(Integer[] cinemaIds) {
		this.cinemaIds = cinemaIds;
	}
}
