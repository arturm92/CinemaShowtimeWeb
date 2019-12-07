package cinemaShowtime.beans;

import javax.faces.bean.ManagedProperty;

import cinemaShowtime.database.model.AccountPreference;
import cinemaShowtime.filters.ReloadInterface;
import cinemaShowtime.utils.DateFormatter;

public abstract class BaseBean implements ReloadInterface {
	
	private boolean prepared = false;
	private DateFormatter dateFormatter = new DateFormatter();

	@ManagedProperty(value = "#{accountBean}")
	private AccountBean accountBean;
	
	@ManagedProperty(value = "#{movieDetailBean}")
	private MovieDetailBean movieDetailBean;
	
	public abstract void reloadPage();
	
	public AccountBean getAccountBean() {
		return accountBean;
	}

	public void setAccountBean(AccountBean accountBean) {
		this.accountBean = accountBean;
	}

	public AccountPreference getAccountPreference() {
		if (accountBean != null) {
			return getAccountBean().getAccountPreferece();
		} else {
			return null;
		}
	}
	
	public boolean isPreferenceHelp() {
		return accountBean.isPreferenceHelp();
	}

	public MovieDetailBean getMovieDetailBean() {
		return movieDetailBean;
	}

	public void setMovieDetailBean(MovieDetailBean movieDetailBean) {
		this.movieDetailBean = movieDetailBean;
	}

	public boolean isPrepared() {
		return prepared;
	}

	public void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}

	public DateFormatter getDateFormatter() {
		return dateFormatter;
	}

}
