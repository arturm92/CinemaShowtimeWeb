package cinemaShowtime.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cinemaShowtime.database.model.Account;
import cinemaShowtime.utils.HibernateSetting;

public class HibernateSession {
	
	private SessionFactory sessionFactory;
	
	public Session initSession() {
		Configuration cfg = new Configuration().setProperty("hibernate.dialect", HibernateSetting.DIALECT)
				.setProperty("hibernate.current_session_context_class", HibernateSetting.SESSION_CONTEXT)
				.setProperty("hibernate.connection.driver_class", HibernateSetting.DRIVER_CLASS)
				.setProperty("hibernate.connection.url", HibernateSetting.URL)
				.setProperty("hibernate.connection.username", HibernateSetting.USERNAME)
				.setProperty("hibernate.connection.password", HibernateSetting.PASSWORD)
				.setProperty("hibernate.connection.pool_size", HibernateSetting.POOL_SIZE)
				.setProperty("hibernate.connection.show_sql", HibernateSetting.SHOW_SQL)
				.addAnnotatedClass(Account.class);

		sessionFactory = cfg.buildSessionFactory();
		return sessionFactory.openSession();
	}
	
	public Session getSession() {
		if (sessionFactory == null) {
			return initSession();
		}
		return sessionFactory.getCurrentSession();
	}
	
}