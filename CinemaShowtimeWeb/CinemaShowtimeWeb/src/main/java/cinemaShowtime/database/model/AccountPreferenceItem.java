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
	private Long accountId;
	@Column(name = "GENRE_ID")
	private Long genreId;
	@Column(name = "CINEMA_ID")
	private Long cinemaId;
	@Column(name = "CITY_ID")
	private Long cityId;

	public AccountPreferenceItem() {
	};

	public AccountPreferenceItem(Long accountId, Long genreId, Long cinemaId, Long cityId) {
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
