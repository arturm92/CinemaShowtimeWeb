package cinemaShowtime.database.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import cinemaShowtime.database.HibernateSession;
import cinemaShowtime.database.model.AccountPreferenceItem;
import cinemaShowtime.utils.Logger;

public class AccountPreferenceItemDAO implements HibernateDAO<AccountPreferenceItem> {

	private HibernateSession hibernateSession = new HibernateSession();

	@SuppressWarnings("finally")
	@Override
	public Long insert(AccountPreferenceItem accountPreferenceItem) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		Long id = null;
		try {
			tx = session.beginTransaction();
			id = (Long) session.save(accountPreferenceItem);
			Logger.log("ACCOUNT PREFERENCE ID : " + id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return id;
		}

	}

	@Override
	public void update(AccountPreferenceItem entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(AccountPreferenceItem item) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("delete from ACCOUNT_PREFERENCE_ITEM where ACCOUNT_ID = :accountId");
			query.setParameter("accountId", item.getAccountId());
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	@Override
	public List<AccountPreferenceItem> findList(HashMap<String, Object> queryParamMap) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		List<AccountPreferenceItem> accPreferenceList = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from ACCOUNT_PREFERENCE_ITEM where ACCOUNT_ID = :accountId");
			for (Map.Entry<String, Object> entry : queryParamMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			accPreferenceList = query.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return accPreferenceList;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	@Override
	public AccountPreferenceItem find(HashMap<String, Object> queryParamMap) {
		Session session = hibernateSession.getSession();
		Transaction tx = null;
		AccountPreferenceItem foundAccPreference = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from ACCOUNT_PREFERENCE_ITEM where ACCOUNT_ID = :accountId");
			for (Map.Entry<String, Object> entry : queryParamMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			List<AccountPreferenceItem> accPreferenceList = query.list();
			for (Iterator<AccountPreferenceItem> iterator = accPreferenceList.iterator(); iterator.hasNext();) {
				foundAccPreference = iterator.next();
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			return foundAccPreference;
		}
	}
}
