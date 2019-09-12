package cinemaShowtime.beans;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.database.dao.AccountDAO;
import cinemaShowtime.database.model.Account;
import cinemaShowtime.utils.Application;

@ManagedBean(name = "loginBean", eager = true)
@SessionScoped
public class LoginBean {

	private String accountName;
	private String accountPassword;
	private Account loggedAccount;

	private AccountDAO accountDAO = new AccountDAO();

	public void registerAccount() {
		Account account = new Account(accountName, accountPassword);
		Integer accountID = accountDAO.insert(account);
		if  (accountID > 0) {
			loginAccount();
		}
	}

	public void loginAccount() {
		if (checkLoginParameter()) {
			HashMap<String,String> queryParamMap = new HashMap<String,String>();
			queryParamMap.put("userName", accountName);
			queryParamMap.put("password", accountPassword);
			loggedAccount = accountDAO.find(queryParamMap);
			if (loggedAccount != null) {
				Application.getInstance().setAccount(loggedAccount);
				goToAccountPage();
			}
		}
	}
	
	private void goToAccountPage() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect("/CinemaShowtimeWeb/account/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logoutAccount() {
		loggedAccount = null;
	}

	private boolean checkLoginParameter() {
		if (accountName == null || accountName.isEmpty() || accountPassword == null || accountPassword.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Błąd logowania!", "Podano niekompletne dane logowania. Spróbuj ponownie."));
			return false;
		} else {
			return true;
		}
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public Account getLoggedAccount() {
		return loggedAccount;
	}

	public void setLoggedAccount(Account loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

}
