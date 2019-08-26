package model.json.movie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import util.DateFormater;

public class MovieFormatted extends Movie {
	
	public String getTrailerURL() {
		if (getTrailers() != null) {
			return getTrailers().get(0).getTrailerFiles().get(0).getUrl().replace("watch?v=", "v/");
		}
		return null;
	}

	public String getRating() {
		if (getImdbRating() != null) {
			return getImdbRating();
		} else {
			return getTmdbRating();
		}
	}

	public String getImdbRating() {
		if (getRatings() != null) {
			Rating imdbRating = getRatings().getImdbRating();
			if (imdbRating != null && !imdbRating.getVoteCount().equals("0")) {
				return imdbRating.getValue() + " / " + imdbRating.getVoteCount() + " ocen";
			}
		}
		return null;
	}

	public String getTmdbRating() {
		if (getRatings() != null) {
			Rating tmdbRating = getRatings().getTmdbRating();
			if (tmdbRating != null && !tmdbRating.getVoteCount().equals("0")) {
				return tmdbRating.getValue() + " / " + tmdbRating.getVoteCount() + " ocen";
			}
		}
		return null;
	}

	public BigDecimal getRatingValue() {
		if (getRatings() != null) {
			Rating imdbRating = getRatings().getImdbRating();
			if (imdbRating != null) {
				return new BigDecimal(imdbRating.getValue());
			}
			Rating tmdbRating = getRatings().getTmdbRating();
			if (tmdbRating != null)
				return new BigDecimal(tmdbRating.getValue());
		}
		return BigDecimal.ZERO;
	}

	public String getGenreInfo() {
		String ret = "gatunek: ";
		if (getGenre() != null) {
			for (Genre elem : getGenre()) {
				ret += "[" + elem.getName() + "] ";
			}
		}
		return ret;
	}

	public String getCrewText() {
		String ret = "";
		if (getCrew() != null) {
			for (Person person : getCrew()) {
				ret += person.getJob() + " : " + person.getName() + "\n";
			}
		}
		return ret;
	}

	public String getCastText() {
		String ret = "";
		if (getCast() != null) {
			for (Person person : getCast()) {
				ret += person.getCharacter() + " : " + person.getName() + "\n";
			}
		}
		return ret;
	}

	public String getFirstDirector() {
		String ret = "";
		if (getCrew() != null) {
			for (Person person : getCrew()) {
				if (person.getJob().equals("reżyser")) {
					ret += person.getJob() + " : " + person.getName() + "\n";
					return ret;
				}
			}
		}
		return ret;
	}
	
	
	public Object getSimpleAgeLimit() {
		if (getAgeLimit() != null) {
			Object val = getAgeLimit().get("PL");
			if (val == null) {
				val = getAgeLimit().get("DE");
				if (val == null) {
					val = getAgeLimit().get("GB");
				}
			}
			return val;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String getReleaseDateFormatted() {
		LinkedHashMap<String, Object> releaseDateMap;
		LinkedHashMap<String, String> map;
		releaseDateMap = getReleaseDate();
		if (releaseDateMap != null) {
			List<Object> date = (ArrayList<Object>) releaseDateMap.get("PL");
			if (date != null) {
				map = (LinkedHashMap<String, String>) date.get(0);
				return "premiera w Polsce: " + map.get("date");
			} else {
				date = (ArrayList<Object>) releaseDateMap.get("US");
				map = (LinkedHashMap<String, String>) date.get(0);
				return "premiera na świecie: " + map.get("date");
			}
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Date getReleaseDateInDateType() {
		LinkedHashMap<String, String> map;
		List<Object> date = (ArrayList<Object>) getReleaseDate().get("PL");
		if (date != null) {
			map = (LinkedHashMap<String, String>) date.get(0);
		} else {
			date = (ArrayList<Object>) getReleaseDate().get("US");
			map = (LinkedHashMap<String, String>) date.get(0);
		}
		DateFormater df = new DateFormater();
		return df.parseString(map.get("date"));
	}

}
