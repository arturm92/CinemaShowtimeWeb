package cinemaShowtime.beans;

import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import cinemaShowtime.utils.HibernateSetting;
import model.database.User;

@ManagedBean(name = "loginBean", eager = true)
@SessionScoped
public class LoginBean {

	private String userName;
	private String userPassword;

	public void registerUser() {
	};

	public void loginUser() {
		Configuration cfg = new Configuration().setProperty("hibernate.dialect", HibernateSetting.DIALECT)
				.setProperty("hibernate.connection.driver_class", HibernateSetting.DRIVER_CLASS)
				.setProperty("hibernate.connection.url", HibernateSetting.URL)
				.setProperty("hibernate.connection.username", HibernateSetting.USERNAME)
				.setProperty("hibernate.connection.password", HibernateSetting.PASSWORD)
				.setProperty("hibernate.connection.pool_size", HibernateSetting.POOL_SIZE)
				.addAnnotatedClass(User.class);

		SessionFactory sessions = cfg.buildSessionFactory();
		Session session = sessions.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<User> userList = session.createQuery("from user").list();
			for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				System.out.println("Id: " + user.getId());
				System.out.println("Name: " + user.getName());
				System.out.println("Password" + user.getPassword());
			}
			tx.commit();
		}

		catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
