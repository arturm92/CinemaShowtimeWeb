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
	private int accountID;
	@Column(name = "GENRE_ID")
	private Integer genreID;
	@Column(name = "CINEMA_ID")
	private Integer cinemaID;

	public AccountPreferenceItem() {
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public Integer getGenreID() {
		return genreID;
	}

	public void setGenreID(Integer genreID) {
		this.genreID = genreID;
	}

	public Integer getCinemaID() {
		return cinemaID;
	}

	public void setCinemaID(Integer cinemaID) {
		this.cinemaID = cinemaID;
	}

}
