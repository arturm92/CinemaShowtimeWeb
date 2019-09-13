package cinemaShowtime.database.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import cinemaShowtime.database.HibernateSession;
import cinemaShowtime.database.model.Account;

public class AccountDAO implements HibernateDAO<Account> {

	private HibernateSession hibernateSession = new HibernateSession();

	@SuppressWarnings({ "finally" })
	@Override
	public Integer insert(Account account) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(account);
			if (id > 0) {
				FacesContext.getCurrentInstance()
						.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Rejestracja zakończona powodzeniem!",
								"Konto użytkownika " + account.getName() + " zostało zarejestrowane pomyślnie."));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Rejestracja zakończona niepowodzeniem!",
						"Konto użytkownika " + account.getName() + "nie zostało zarejestrowane. Spróbuj ponownie."));
			}
			tx.commit();
		} catch (HibernateException e) {
			if (e instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance()
						.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Rejestracja zakończona niepowodzeniem!",
								"Istnieje już w systemie  konto o nazwie " + account.getName() + ".Spróbuj ponownie."));
			}
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return id;
		}

	}

	@Override
	public void update(Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Account entity) {
		//BRAK
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	@Override
	public List<Account> findList(HashMap<String, Object> queryParamMap) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		List<Account> accountList = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from ACCOUNT");
			accountList = query.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return accountList;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	@Override
	public Account find(HashMap<String, Object> queryParamMap) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		Account foundAccount = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from ACCOUNT where USERNAME = :userName and PASSWORD = :password");
			for (Map.Entry<String, Object> entry : queryParamMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			List<Account> accountList = query.list();
			if (accountList.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Błąd logowania!", "\nPodano nieprawidłowe dane logowania. Spróbuj ponownie."));
			}
			for (Iterator<Account> iterator = accountList.iterator(); iterator.hasNext();) {
				foundAccount = iterator.next();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Zalogowano!", foundAccount.getName()));
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return foundAccount;
		}
	}

}
