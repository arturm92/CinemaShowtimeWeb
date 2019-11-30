package cinemaShowtime.beans;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import cinemaShowtime.database.dao.AccountDAO;
import cinemaShowtime.database.model.Account;
import cinemaShowtime.utils.Logger;

@ManagedBean(name = "loginBean", eager = true)
@SessionScoped
public class LoginBean {

	private String accountName;
	private String accountPassword;
	private Account loggedAccount;

	private AccountDAO accountDAO = new AccountDAO();

	public LoginBean() {
		Logger.logCreateBeanInfo("LoginBean");
	}
	
	@PostConstruct
	public void init() {
		long startTime = System.currentTimeMillis();
		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	public void registerAccount() {
		Account account = new Account(accountName, accountPassword);
		Long accountId = accountDAO.insert(account);
		if (accountId != null) {
			loginAccount();
		}
	}

	public void loginAccount() {
		if (checkLoginParameter()) {
			HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
			queryParamMap.put("userName", accountName);
			queryParamMap.put("password", accountPassword);
			loggedAccount = accountDAO.find(queryParamMap);
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
