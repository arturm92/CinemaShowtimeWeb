package cinemaShowtime.utils;

public class Logger {

	public static void log(String message) {
		if (Const.LOGGER) {
			System.out.println(message);
		}
	}

	public static void logBeanStartTime(String beanName, long time) {
		log(beanName + " started in " + ((time) / 1000) + " second");
	}
	
	public static void logCreateBeanInfo(String beanName) {
		log("CREATE " + beanName);
	}
}
