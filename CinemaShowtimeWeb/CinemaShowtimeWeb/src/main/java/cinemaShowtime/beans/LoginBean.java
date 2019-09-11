package cinemaShowtime.beans;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import cinemaShowtime.database.HibernateSession;
import cinemaShowtime.database.model.Account;
import cinemaShowtime.utils.HibernateSetting;

@ManagedBean(name = "loginBean", eager = true)
@SessionScoped
public class LoginBean {

	private String accountName;
	private String accountPassword;
	private Account loggedAccount;
	private HibernateSession hibernateSession = new HibernateSession();

	public void registerAccount() {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Account account = new Account(accountName,accountPassword);
			Integer accountID = (Integer) session.save(account);
			System.out.println(accountID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void loginAccount() {
		if (checkLoginParameter()) {
			Session session = hibernateSession.getSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Query query = session.createQuery("from ACCOUNT where USERNAME = :userName and PASSWORD = :password");
				query.setParameter("userName", accountName);
				query.setParameter("password", accountPassword);
				List<Account> accountList = query.list();
				if (accountList.isEmpty()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Błąd logowania!", "\nPodano nieprawidłowe dane logowania. Spróbuj ponownie."));
				}
				for (Iterator<Account> iterator = accountList.iterator(); iterator.hasNext();) {
					loggedAccount = iterator.next();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Zalogowano!", loggedAccount.getName()));
				}
				tx.commit();
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
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
