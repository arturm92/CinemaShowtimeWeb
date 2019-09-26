package cinemaShowtime.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ACCOUNT_PREFERENCE_ITEM")
public class AccountPreferenceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "ACCOUNT_ID")
	private Integer accountId;
	@Column(name = "GENRE_ID")
	private Integer genreId;
	@Column(name = "CINEMA_ID")
	private Integer cinemaId;
	@Column(name = "CITY_ID")
	private Integer cityId;

	public AccountPreferenceItem() {
	};

	public AccountPreferenceItem(Integer accountId, Integer genreId, Integer cinemaId, Integer cityId) {
		this.accountId = accountId;
		this.genreId = genreId;
		this.cinemaId = cinemaId;
		this.cityId = cityId;
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	public Integer getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Integer cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}
