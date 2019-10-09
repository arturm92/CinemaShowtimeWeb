package cinemaShowtime.database.model;

public class AccountPreference {

	private Long accountId;
	private Long cityId;
	private Long[] genreIds;
	private Long[] cinemaIds;

	public AccountPreference() {
		this.genreIds = new Long[] {};
		this.cinemaIds = new Long[] {};
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long[] getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(Long[] genreIds) {
		this.genreIds = genreIds;
	}

	public Long[] getCinemaIds() {
		return cinemaIds;
	}

	public void setCinemaIds(Long[] cinemaIds) {
		this.cinemaIds = cinemaIds;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}
