package cinemaShowtime.utils;

public class Const {
	
	public static final String LANGUAGE = "pl";
	public static final String MULTIMEDIA_LANGUAGE = "en";
	public static final String COUNTRIES = "PL,US";
	
	public static final String BASE_URL = "https://api.internationalshowtimes.com/v4/";
	public static final String API_KEY = "p4kJT5hqruzwzqqyNlUhbtXtMhZndaWN";
	
	public static final String LOCATION_BASE_URL = "https://api.ipgeolocation.io/ipgeo";
	public static final String LOCATION_API_KEY= "7e0c13dce3d54d898899d8d501003bc3";

	public static final String SHOWTIMES = BASE_URL + "showtimes";
	public static final String CINEMAS = BASE_URL + "cinemas";
	public static final String MOVIES = BASE_URL + "movies";
	public static final String CHANINS  = BASE_URL + "chains";
	public static final String CITIES  = BASE_URL + "cities";
	public static final String GENRES = BASE_URL + "genres";
	
	public static final String TIMEZONE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final String TIME_DATE_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm";
	
	public static final String DEFAULT_POSTER = "/resources/img/default_poster.jpg";
	public static final int DISTANCE = 25;
	public static final boolean LOGGER = false;
}
