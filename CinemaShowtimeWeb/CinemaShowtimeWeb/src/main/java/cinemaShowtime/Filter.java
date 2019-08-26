package cinemaShowtime;

import java.util.HashMap;
import java.util.Map;

public class Filter {

	private HashMap<String, String> filterParameter;
	
	public static final String LANG = "lang";
	public static final String COUNTRIES = "countries";
	public static final String INCLUDE_OUTDATED = "include_outdated";
	public static final String INCLUDE_UPCOMINGS = "include_upcomings";
	public static final String RELEASE_DATE_FROM = "release_date_from";
	public static final String RELEASE_DATE_TO = "release_date_to";
	public static final String GENRE_IDS = "genre_ids";
	
	
	
	public Filter() {
		filterParameter = new HashMap<String, String>();
	}

	public void addParam(String key, String value) {
		filterParameter.put(key, value);
	}
	
	public void deleteParam(String key) {
		filterParameter.remove(key);
	}

	@SuppressWarnings("rawtypes")
	public String prepareParameters() {
		String parameters = "";
		for (Map.Entry me : filterParameter.entrySet()) {
			parameters += "&" + me.getKey() + "=" + me.getValue();
		}
		return parameters;
	}

}
