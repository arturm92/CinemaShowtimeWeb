package cinemaShowtime.context;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cinemaShowtime.utils.HibernateSetting;
import cinemaShowtime.utils.Logger;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger.log("INITIALIZE APPLICATION");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Logger.log("DESTROY APPLICATION");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				Logger.log("DRIVER UNREGISTER");
			} catch (SQLException e) {
				Logger.log("DRIVER UNREGISTER ERROR : " + e.getMessage());
			}
		}
	}
}