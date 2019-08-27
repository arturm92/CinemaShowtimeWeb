package cinemaShowtime;

import java.util.HashMap;
import java.util.Map;

public class Filter {

	private HashMap<String, String> filterParameter;
	private HashMap<String, String> queryParameter;
	private String fields;

	public class Parameter {
		public static final String LANG = "lang";
		public static final String COUNTRIES = "countries";
		public static final String INCLUDE_OUTDATED = "include_outdated";
		public static final String INCLUDE_UPCOMINGS = "include_upcomings";
		public static final String RELEASE_DATE_FROM = "release_date_from";
		public static final String RELEASE_DATE_TO = "release_date_to";
		public static final String GENRE_IDS = "genre_ids";
		public static final String LOCATION = "location";
		public static final String DISTANCE = "distance";
	}

	public class Query {
		public static final String MOVIE_ID = "movie_id";
		public static final String CINEMA_ID = "cinema_id";
	}

	public class Field {
		public static final String MOVIE_STANDARD_FIELDS = "id,title,original_title,poster_image.flat,release_dates,ratings,genres,crew";
		public static final String MOVIE_POSTER_FIELDS = "id,poster_image.flat";
	}

	private boolean isFirst;

	public Filter() {
		queryParameter = new HashMap<String, String>();
		filterParameter = new HashMap<String, String>();
	}

	public void addFilterParam(String key, String value) {
		filterParameter.put(key, value);
	}

	public void deleteFilterParam(String key) {
		filterParameter.remove(key);
	}

	public void addQueryParam(String key, String value) {
		queryParameter.put(key, value);
	}

	public void deleteQueryParam(String key) {
		queryParameter.remove(key);
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
	@SuppressWarnings("rawtypes")
	public String prepareParameters() {
		isFirst = true;
		String parameters = "";
		for (Map.Entry me : queryParameter.entrySet()) {
			parameters += getOperator() + me.getKey() + "=" + me.getValue();
		}
		if (fields != null) {
			parameters += getOperator() + "fields=" + fields;
		}
		for (Map.Entry me : filterParameter.entrySet()) {
			parameters += getOperator() + me.getKey() + "=" + me.getValue();
		}
		return parameters;
	}

	private String getOperator() {
		if (isFirst) {
			isFirst = false;
			return "?";
		} else {
			return "&";
		}
	}

}
