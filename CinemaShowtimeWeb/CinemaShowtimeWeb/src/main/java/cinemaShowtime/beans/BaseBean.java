package cinemaShowtime.beans;

import javax.faces.bean.ManagedProperty;

import cinemaShowtime.database.model.AccountPreference;

public class BaseBean {
	
	@ManagedProperty(value = "#{accountBean}")
	private AccountBean accountBean;
	
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
}
