package cinemaShowtime.database.model;

public class AccountPreference {

	private int accountId;
	private Integer cityId;
	private Integer[] genreIds;
	private Integer[] cinemaIds;

	public AccountPreference() {
		this.genreIds = new Integer[] {};
		this.cinemaIds = new Integer[] {};
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
